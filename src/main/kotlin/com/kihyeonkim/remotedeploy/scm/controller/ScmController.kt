package com.kihyeonkim.remotedeploy.scm.controller

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.scm.service.ScmService
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
	fun addScm():DeployResponse<*>{
		return scmService.getKeyAliasList()
	}

	@GetMapping("/repositoryList")
	@ResponseBody
	fun getScmList(
		@RequestParam(
			name = "scmInfoAlias",
			required = true
		) scmInfoAlias: String
	): DeployResponse<*> {
		return scmService.getRepositoryList(scmInfoAlias)
	}
}