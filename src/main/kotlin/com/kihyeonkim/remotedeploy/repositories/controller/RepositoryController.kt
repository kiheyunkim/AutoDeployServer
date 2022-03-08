package com.kihyeonkim.remotedeploy.repositories.controller

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.repositories.model.Repository
import com.kihyeonkim.remotedeploy.repositories.service.RepositoryService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-09
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Controller
@Api("레포지토리 관리")
@RequestMapping("/repository")
class RepositoryController(private val repositoryService: RepositoryService) {
	@GetMapping("/list")
	@ApiOperation("레포지토리 목록 조회")
	@ResponseBody
	fun getRepositoryList(
		@RequestParam(
			name = "scmInfoAlias",
			required = true
		) scmInfoAlias: String,
		@RequestParam(
			name = "page", required = true, defaultValue = "1"
		) page: Int
	): DeployResponse<*> {
		return repositoryService.getRepositoryList(scmInfoAlias)
	}

	@GetMapping("/detail")
	@ApiOperation("레포지토리 상세 조회")
	@ResponseBody
	fun getRepositoryDetail(
		@RequestParam(
			name = "scmInfoAlias",
			required = true
		) scmInfoAlias: String
	): DeployResponse<*> {
		return repositoryService.getRepositoryDetail()
	}

	@PostMapping("/add")
	@ResponseBody
	fun postAddScm(@RequestBody repository: Repository): DeployResponse<*> {
		return repositoryService.addScm(repository)
	}

	@PostMapping("/update")
	@ResponseBody
	fun postUpdateScm(@RequestBody repository: Repository): DeployResponse<*> {
		return repositoryService.addScm(repository)
	}

	@DeleteMapping("/delete")
	@ResponseBody
	fun deleteScm(@RequestBody scmAlias: String): DeployResponse<*> {
		return repositoryService.deleteScm(scmAlias);
	}
}