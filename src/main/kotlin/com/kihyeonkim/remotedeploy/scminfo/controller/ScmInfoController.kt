package com.kihyeonkim.remotedeploy.scminfo.controller

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.scminfo.service.ScmInfoService
import com.kihyeonkim.remotedeploy.scminfo.vo.ScmInfoVo
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
@RequestMapping("/scmInfo")
class ScmInfoController(private val scmInfoService: ScmInfoService) {
	@GetMapping("/list")
	fun getScmInfoList(): DeployResponse<*> {
		return scmInfoService.getSCMInfoList()
	}

	@PostMapping("/add")
	fun postGithubApi(scmInfoVo: ScmInfoVo): DeployResponse<*> {
		return scmInfoService.addSCMInfo(scmInfoVo)
	}

	@PostMapping("/modify")
	fun postModifyGithubApi(scmInfoVo: ScmInfoVo): DeployResponse<*> {
		return scmInfoService.modifySCMInfo(scmInfoVo)
	}

	@PostMapping("/delete")
	fun deleteGithubApi(repoAlias: String): DeployResponse<*> {
		return scmInfoService.deleteSCMInfo(repoAlias)
	}
}