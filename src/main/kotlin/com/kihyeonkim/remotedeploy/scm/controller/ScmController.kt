package com.kihyeonkim.remotedeploy.scm.controller

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.scm.service.ScmService
import com.kihyeonkim.remotedeploy.scm.vo.ScmVo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-09
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Controller
@RequestMapping("/scm")
class ScmController(private val scmService: ScmService) {

	@GetMapping("/keyAliasList")
	@ResponseBody
	fun getKeyAliasList(): DeployResponse<*> {
		return scmService.getKeyAliasList()
	}

	@GetMapping("/repositoryList")
	@ResponseBody
	fun getRepositoryList(
		@RequestParam(
			name = "scmInfoAlias",
			required = true
		) scmInfoAlias: String
	): DeployResponse<*> {
		return scmService.getRepositoryList(scmInfoAlias)
	}

	@GetMapping("/list")
	@ResponseBody
	fun getScmList(@RequestParam(name = "page", required = true, defaultValue = "1") page: Int): DeployResponse<*> {
		return scmService.getScmList(page)
	}

	@PostMapping("/add")
	@ResponseBody
	fun postAddScm(@RequestBody scmVo: ScmVo): DeployResponse<*> {
		return scmService.addScm(scmVo)
	}

	@PostMapping("/update")
	@ResponseBody
	fun postUpdateScm(@RequestBody scmVo: ScmVo): DeployResponse<*> {
		return scmService.addScm(scmVo)
	}

	@DeleteMapping("/delete")
	@ResponseBody
	fun deleteScm(@RequestBody scmAlias: String): DeployResponse<*>{
		return scmService.deleteScm(scmAlias);
	}
}