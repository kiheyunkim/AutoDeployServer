package com.kihyeonkim.remotedeploy.deploy.controller


import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.jenkins.api.Jenkins
import com.kihyeonkim.remotedeploy.jenkins.enumeration.BuildType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-07
 * Github : http://github.com/kiheyunkim
 * Comment :
 */
@RestController
@RequestMapping("/deploy")
class DeployController(private val jenkins: Jenkins) {
	@PostMapping("/create")
	fun postCreateJenkinsJob(jobName: String, gitUrl: String, builderType: String): DeployResponse<*> {

		val buildType: BuildType = BuildType.valueOf(builderType);

		return jenkins.createJenkinsJob(jobName, gitUrl, buildType)
	}

	@DeleteMapping("/delete")
	fun deleteJenkinsJob(jobName: String): DeployResponse<*> {
		return jenkins.deleteJenkinsJob(jobName)
	}
}