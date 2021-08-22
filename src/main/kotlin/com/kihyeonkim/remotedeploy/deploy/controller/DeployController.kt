package com.kihyeonkim.remotedeploy.deploy.controller


import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.deploy.service.DeployService
import com.kihyeonkim.remotedeploy.jenkins.api.JenkinsApi
import com.kihyeonkim.remotedeploy.jenkins.enumeration.BuildType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-07
 * Github : http://github.com/kiheyunkim
 * Comment :
 */
@Controller
@RequestMapping("/deploy")
class DeployController(private val deployService: DeployService) {
	@GetMapping
	fun getIndex(): String {
		return "index"
	}

	@PostMapping("/create")
	@ResponseBody
	fun postCreateJenkinsJob(repoAlias: String, repositoryName: String, builderType: String): DeployResponse<*> {
		val buildType: BuildType = BuildType.valueOf(builderType);

		return deployService.createDeployJob(repoAlias, repositoryName, buildType)
	}

	@PostMapping("/startBuild")
	@ResponseBody
	fun postStartJenkinsJob(jobName: String, params: Map<String, List<String>>?): DeployResponse<*> {
		return deployService.startJenkinsJob(jobName, params)
	}

	@DeleteMapping("/delete")
	@ResponseBody
	fun deleteJenkinsJob(jobName: String): DeployResponse<*> {
		return deployService.deleteJenkinsJob(jobName)
	}

	@GetMapping("/deployProgress")
	@ResponseBody   //ToDo: 소켓으로 바꾸는 것 고려
	fun getDeployProgress(jobName: String, jobNumber: Int, start: Int?): DeployResponse<*> {
		return deployService.getDeployProgress(jobName, jobNumber, start)
	}
}