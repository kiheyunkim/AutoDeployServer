package com.kihyeonkim.remotedeploy.deploy.controller


import com.kihyeonkim.remotedeploy.jenkins.api.Jenkins
import com.kihyeonkim.remotedeploy.jenkins.enumeration.BuildType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-07
 * Github : http://github.com/kiheyunkim
 * Comment :
 */
@Controller
@RequestMapping("/deploy")
class DeployController(private val jenkins: Jenkins) {
	@GetMapping
	@ResponseBody
	fun getTest(): String {

		jenkins.createJenkinsJob("textJob","http://www.naver.com", BuildType.MAVEN)

		/*val templateEngine = TemplateEngine()
		val templateResolver = StringTemplateResolver();
		templateResolver.templateMode = TemplateMode.XML
		templateEngine.setTemplateResolver(templateResolver)

		val context = Context(Locale.ENGLISH)
		context.setVariable("gitUrl", "https://www.naver.com")

		val file = ClassPathResource("jenkinsTemplate/jobCreateTemplate.xml")
		val result = IOUtils.toString(FileInputStream(file.file), Charsets.UTF_8)

		val s = templateEngine.process(result, context)
		println(s);

		val jenkinsClient: JenkinsClient = JenkinsClient.builder()
			.endPoint("http://127.0.0.1:9000")
			.credentials("kihyeonkim:jenkins")
			.build()


		//val systemInfo: SystemInfo = jenkinsClient.api().systemApi().systemInfo()*/


		return "OK"
	}

}