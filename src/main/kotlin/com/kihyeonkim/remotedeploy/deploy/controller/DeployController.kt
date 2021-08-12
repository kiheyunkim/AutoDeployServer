package com.kihyeonkim.remotedeploy.deploy.controller


import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.jenkins.api.Jenkins
import com.kihyeonkim.remotedeploy.jenkins.enumeration.BuildType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
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
	@PostMapping("/create")
	@ResponseBody
	fun getTest(jobName: String, gitUrl: String, builderType: String): DeployResponse<*> {

		val buildType: BuildType = BuildType.valueOf(builderType);

		return jenkins.createJenkinsJob(jobName, gitUrl, buildType)
	}

}