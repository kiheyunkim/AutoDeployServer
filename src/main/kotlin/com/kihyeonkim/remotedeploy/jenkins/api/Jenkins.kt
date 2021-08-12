package com.kihyeonkim.remotedeploy.jenkins.api

import com.cdancy.jenkins.rest.JenkinsClient
import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.jenkins.enumeration.BuildType
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
class Jenkins(
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

	fun createJenkinsJob(jobName: String, gitUrl: String, buildType: BuildType): DeployResponse<*> {
		val jobCreateTemplateResource = ClassPathResource("jenkinsTemplate/jobCreateTemplate.xml")
		val jobCreateTemplateFile = IOUtils.toString(FileInputStream(jobCreateTemplateResource.file), Charsets.UTF_8)


		val jobBuilderResource = when (buildType) {
			BuildType.MAVEN -> ClassPathResource("jenkinsTemplate/buildTemplate/mavenBuild.xml")
			BuildType.GRADLE -> ClassPathResource("jenkinsTemplate/buildTemplate/mavenBuild.xml")
			BuildType.OTHER -> ClassPathResource("jenkinsTemplate/buildTemplate/mavenBuild.xml")
		}
		val jobBuilderTemplateFile = IOUtils.toString(FileInputStream(jobBuilderResource.file), Charsets.UTF_8)
		val jenkinsBuilderInfoXml = templateEngine.process(jobBuilderTemplateFile, Context())

		val context = Context(Locale.ENGLISH)
		context.setVariable("description", "DEPLOY_${jobName.uppercase()}")
		context.setVariable("gitUrl", gitUrl)
		context.setVariable("builder", jenkinsBuilderInfoXml)

		val jenkinsConfigXml = templateEngine.process(jobCreateTemplateFile, context)
		val requestStatus = jenkinsClient.api().jobsApi().create(null, jobName, jenkinsConfigXml)

		return DeployResponse(requestStatus.value())
	}
}