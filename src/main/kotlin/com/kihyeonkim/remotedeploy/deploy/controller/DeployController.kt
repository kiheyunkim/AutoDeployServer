package com.kihyeonkim.remotedeploy.deploy.controller


import com.cdancy.jenkins.rest.JenkinsClient
import com.cdancy.jenkins.rest.domain.system.SystemInfo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

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
	fun getTest() {
		val jenkinsClient: JenkinsClient = JenkinsClient.builder()
			.endPoint("http://127.0.0.1:9000")
			.credentials("kihyeonkim:jenkins")
			.build()

		val systemInfo: SystemInfo = jenkinsClient.api().systemApi().systemInfo()
		println(systemInfo)
	}

}