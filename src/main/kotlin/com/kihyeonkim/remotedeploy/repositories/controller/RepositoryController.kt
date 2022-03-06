package com.kihyeonkim.remotedeploy.repositories.controller

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.repositories.service.RepositoryService
import com.kihyeonkim.remotedeploy.repositories.vo.ScmVo
import org.springframework.web.bind.annotation.*

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-09
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
//@Controller
@RequestMapping("/repository")
class RepositoryController(private val repositoryService: RepositoryService) {
/*
	@GetMapping("/list")
	@ResponseBody
	fun getRepositoryList(
		@RequestParam(
			name = "scmInfoAlias",
			required = true
		) scmInfoAlias: String
	): DeployResponse<*> {
		return repositoryService.getRepositoryList(scmInfoAlias)
	}

	@GetMapping("/list")
	@ResponseBody
	fun getScmList(@RequestParam(name = "page", required = true, defaultValue = "1") page: Int): DeployResponse<*> {
		return repositoryService.getScmList(page)
	}

	@PostMapping("/add")
	@ResponseBody
	fun postAddScm(@RequestBody scmVo: ScmVo): DeployResponse<*> {
		return repositoryService.addScm(scmVo)
	}

	@PostMapping("/update")
	@ResponseBody
	fun postUpdateScm(@RequestBody scmVo: ScmVo): DeployResponse<*> {
		return repositoryService.addScm(scmVo)
	}

	@DeleteMapping("/delete")
	@ResponseBody
	fun deleteScm(@RequestBody scmAlias: String): DeployResponse<*>{
		return repositoryService.deleteScm(scmAlias);
	}
	*/
}