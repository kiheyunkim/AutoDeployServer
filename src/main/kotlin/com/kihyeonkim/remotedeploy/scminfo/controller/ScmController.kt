package com.kihyeonkim.remotedeploy.scminfo.controller

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.scminfo.model.ScmInfo
import com.kihyeonkim.remotedeploy.scminfo.service.ScmService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-24
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@RestController
@Api("형상관리 계정 정보 등록")
@RequestMapping("/scmInfo")
class ScmController(private val scmService: ScmService) {

	@ApiOperation(value = "형상관리 접속 정보 리스트")
	@GetMapping("/list")
	fun getScmInfoList(): DeployResponse<*> {
		return scmService.getSCMInfoList()
	}

	@ApiOperation(value = "형상관리 접속 정보 추가")
	@PostMapping("/add")
	fun postGithubApi(@RequestBody scmInfo: ScmInfo): DeployResponse<*> {
		return scmService.addSCMInfo(scmInfo)
	}

	@ApiOperation(value = "형상관리 접속 정보 수정")
	@PostMapping("/modify")
	fun postModifyGithubApi(@RequestBody scmInfo: ScmInfo): DeployResponse<*> {
		return scmService.modifySCMInfo(scmInfo)
	}

	@ApiOperation(value = "형상관리 접속 정보 삭제")
	@PostMapping("/delete")
	fun deleteGithubApi(@RequestBody repoAlias: String): DeployResponse<*> {
		return scmService.deleteSCMInfo(repoAlias)
	}
}