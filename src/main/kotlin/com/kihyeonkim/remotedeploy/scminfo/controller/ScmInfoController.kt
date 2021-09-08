package com.kihyeonkim.remotedeploy.scminfo.controller

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.scminfo.service.ScmInfoService
import com.kihyeonkim.remotedeploy.scminfo.vo.ScmInfoVo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

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
	@ResponseBody
	fun getScmInfoList(): DeployResponse<*> {
		return scmInfoService.getSCMInfoList()
	}

	@PostMapping("/add")
	@ResponseBody
	fun postGithubApi(@RequestBody scmInfoVo: ScmInfoVo): DeployResponse<*> {
		return scmInfoService.addSCMInfo(scmInfoVo)
	}

	@PostMapping("/modify")
	@ResponseBody
	fun postModifyGithubApi(@RequestBody scmInfoVo: ScmInfoVo): DeployResponse<*> {
		return scmInfoService.modifySCMInfo(scmInfoVo)
	}

	@PostMapping("/delete")
	@ResponseBody
	fun deleteGithubApi(@RequestBody repoAlias: String): DeployResponse<*> {
		return scmInfoService.deleteSCMInfo(repoAlias)
	}
}