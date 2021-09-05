package com.kihyeonkim.remotedeploy.scm.controller

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.scm.service.SCMService
import com.kihyeonkim.remotedeploy.scm.vo.ScmInfoVo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-24
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Controller
@RequestMapping("/scm")
class SCMController(private val scmService: SCMService) {
	@GetMapping("/list")
	fun getScmInfoList(): DeployResponse<*> {
		return scmService.getSCMInfoList()
	}

	@PostMapping("/add")
	fun postGithubApi(scmInfoVo: ScmInfoVo): DeployResponse<*> {
		return scmService.addSCMInfo(scmInfoVo)
	}

	@PostMapping("/modify")
	fun postModifyGithubApi(scmInfoVo: ScmInfoVo): DeployResponse<*> {
		return scmService.modifySCMInfo(scmInfoVo)
	}

	@PostMapping("/delete")
	fun deleteGithubApi(repoAlias: String): DeployResponse<*> {
		return scmService.deleteSCMInfo(repoAlias)
	}
}