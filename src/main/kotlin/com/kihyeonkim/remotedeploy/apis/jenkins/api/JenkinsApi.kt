package com.kihyeonkim.remotedeploy.apis.jenkins.api

import com.cdancy.jenkins.rest.JenkinsClient
import com.cdancy.jenkins.rest.domain.common.RequestStatus
import com.kihyeonkim.remotedeploy.apis.jenkins.enumeration.BuildType
import com.kihyeonkim.remotedeploy.apis.jenkins.model.JenkinsLog
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.StringTemplateResolver
import java.io.FileInputStream
import java.util.*

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-12
 * Github : http://github.com/kiheyunkim
 * Comment :
 */
@Component
class JenkinsApi(
	@Value("\${jenkins.jenkinsUrl}")
	private var jenkinsUrl: String,

	@Value("\${jenkins.jenkinsPort}")
	private var jenkinsPort: String,

	@Value("\${jenkins.jenkinsId}")
	private var jenkinsId: String,

	@Value("\${jenkins.jenkinsPassword}")
	private var jenkinsPassword: String
) {
	private val jenkinsClient: JenkinsClient = JenkinsClient.builder()
		.endPoint("$jenkinsUrl:$jenkinsPort")
		.credentials("$jenkinsId:$jenkinsPassword")
		.build()

	private val templateEngine: TemplateEngine = TemplateEngine()

	init {
		val templateResolver = StringTemplateResolver();
		templateResolver.templateMode = TemplateMode.XML
		templateEngine.setTemplateResolver(templateResolver)
	}

	fun createJenkinsJob(jobName: String, gitUrl: String, buildType: BuildType): RequestStatus {
		val jobCreateTemplateResource = ClassPathResource("jenkinsTemplate/jobCreateTemplate.xml")
		val jobCreateTemplateFile = IOUtils.toString(FileInputStream(jobCreateTemplateResource.file), Charsets.UTF_8)


		val jobBuilderResource = when (buildType) {
			BuildType.MAVEN -> ClassPathResource("jenkinsTemplate/buildTemplate/mavenBuild.xml")
			BuildType.GRADLE -> ClassPathResource("jenkinsTemplate/buildTemplate/gradleBuild.xml")
			BuildType.OTHER -> ClassPathResource("jenkinsTemplate/buildTemplate/mavenBuild.xml")
		}
		val jobBuilderTemplateFile = IOUtils.toString(FileInputStream(jobBuilderResource.file), Charsets.UTF_8)
		val jenkinsBuilderInfoXml = templateEngine.process(jobBuilderTemplateFile, Context())

		val context = Context(Locale.ENGLISH)
		context.setVariable("description", "DEPLOY_${jobName.uppercase()}")
		context.setVariable("gitUrl", gitUrl)
		context.setVariable("builder", jenkinsBuilderInfoXml)

		val jenkinsConfigXml = templateEngine.process(jobCreateTemplateFile, context)

		return jenkinsClient.api().jobsApi().create(null, jobName, jenkinsConfigXml)
	}

	fun startJenkinsJob(jobName: String, params: Map<String, List<String>>?): Int {
		val requestStatus = if (params == null) {
			jenkinsClient.api().jobsApi().build(null, jobName)
		} else {
			jenkinsClient.api().jobsApi().buildWithParameters(null, jobName, params)
		}

		return requestStatus.value()
	}

	fun getJenkinsLog(jobName: String, jobNumber: Int, start: Int?): JenkinsLog {
		val progressiveText = if (start == null) {
			jenkinsClient.api().jobsApi().progressiveText(null, jobName, jobNumber)
		} else {
			jenkinsClient.api().jobsApi().progressiveText(null, jobName, jobNumber, start.toInt())
		}

		return JenkinsLog(progressiveText.text(), progressiveText.size(), progressiveText.hasMoreData())
	}

	fun deleteJenkinsJob(jobName: String): RequestStatus {
		return jenkinsClient.api().jobsApi().delete(null, jobName)
	}
}