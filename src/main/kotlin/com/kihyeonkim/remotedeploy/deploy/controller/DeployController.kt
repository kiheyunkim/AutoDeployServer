package com.kihyeonkim.remotedeploy.deploy.controller


import com.cdancy.jenkins.rest.JenkinsClient
import com.cdancy.jenkins.rest.domain.system.SystemInfo
import freemarker.template.Configuration
import freemarker.template.TemplateExceptionHandler
import org.apache.commons.io.IOUtils
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.StringTemplateResolver
import org.xml.sax.InputSource
import java.io.FileInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.io.StringReader
import java.util.*

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-07
 * Github : http://github.com/kiheyunkim
 * Comment :
 */
@Controller
@RequestMapping("/deploy")
class DeployController {
	@GetMapping
	@ResponseBody
	fun getTest(): String {

		val templateEngine = TemplateEngine()
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

		//val systemInfo: SystemInfo = jenkinsClient.api().systemApi().systemInfo()


		return "OK"
	}

}