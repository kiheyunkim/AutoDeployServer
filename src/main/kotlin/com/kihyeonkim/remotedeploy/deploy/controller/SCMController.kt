package com.kihyeonkim.remotedeploy.deploy.controller

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.deploy.model.ScmModel
import com.kihyeonkim.remotedeploy.deploy.service.SCMService
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
	fun postGithubApi(scmModel: ScmModel): DeployResponse<*> {
		return scmService.addSCMInfoList(scmModel)
	}

	@PostMapping("/modify")
	fun postModifyGithubApi(scmModel: ScmModel): DeployResponse<*> {
		return scmService.modifySCMInfo(scmModel)
	}

	@PostMapping("/delete")
	fun deleteGithubApi(repoAlias: String): DeployResponse<*> {
		return scmService.deleteSCMInfo(repoAlias)
	}
}