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
			name = "page",
			required = true,
			defaultValue = "1"
		) page: Int
	): DeployResponse<*> {
		return repositoryService.getRepositoryList(page)
	}

	@GetMapping("/detail")
	@ApiOperation("레포지토리 상세 조회")
	@ResponseBody
	fun getRepositoryDetail(
		@RequestParam(
			name = "repositoryAlias",
			required = true
		) repositoryAlias: String
	): DeployResponse<*> {
		return repositoryService.getRepositoryDetail(repositoryAlias)
	}

	@PostMapping("/add")
	@ApiOperation("레포지토리 정보 추가")
	@ResponseBody
	fun postAddScm(@RequestBody repository: Repository): DeployResponse<Boolean?> {
		return repositoryService.addRepositoryInfo(repository)
	}

	@PostMapping("/update")
	@ApiOperation("레포지토리 정보 수정")
	@ResponseBody
	fun postUpdateScm(@RequestBody repository: Repository): DeployResponse<Boolean?> {
		return repositoryService.updateRepositoryInfo(repository)
	}

	@DeleteMapping("/delete")
	@ApiOperation("레포지토리 정보 삭제")
	@ResponseBody
	fun deleteScm(@RequestBody scmAlias: String): DeployResponse<Boolean?> {
		return repositoryService.deleteRepositoryInfo(scmAlias);
	}
}