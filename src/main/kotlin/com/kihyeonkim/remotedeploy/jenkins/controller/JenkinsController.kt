package com.kihyeonkim.remotedeploy.jenkins.controller

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.jenkins.model.Jenkins
import com.kihyeonkim.remotedeploy.jenkins.service.JenkinsService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@Api("젠킨스 등록 및 조회")
@RequestMapping("/jenkins")
class JenkinsController(private val jenkinsService: JenkinsService) {
	@GetMapping("/list")
	@ApiOperation("젠킨스 목록 조회")
	fun getJenkinsList(
		@RequestParam(
			name = "page",
			required = true,
			defaultValue = "1"
		) page: Int
	): DeployResponse<*> {
		return jenkinsService.getJenkinsList(page)
	}

	@PutMapping("/add")
	@ApiOperation("젠킨스 등록")
	fun putJenkinsInfo(@RequestBody jenkins: Jenkins): DeployResponse<Boolean?> {
		return jenkinsService.putJenkinsInfo(jenkins)
	}

	@DeleteMapping("/delete")
	@ApiOperation("젠킨스 삭제")
	fun deleteJenkinsInfo(jenkinsId: String): DeployResponse<Boolean> {
		return jenkinsService.deleteJenkinsInfo(jenkinsId)
	}
}