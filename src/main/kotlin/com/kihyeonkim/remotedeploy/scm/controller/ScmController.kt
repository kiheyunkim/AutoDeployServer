package com.kihyeonkim.remotedeploy.scm.controller

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.scm.service.ScmService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-09
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Controller
@RequestMapping("/scm")
class ScmController(private val scmService: ScmService) {

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

}