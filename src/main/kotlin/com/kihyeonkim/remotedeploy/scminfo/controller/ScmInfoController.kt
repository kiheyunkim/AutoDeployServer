package com.kihyeonkim.remotedeploy.scminfo.controller

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.scminfo.service.ScmInfoService
import com.kihyeonkim.remotedeploy.scminfo.vo.ScmInfoVo
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.stereotype.Controller
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
class ScmInfoController(private val scmInfoService: ScmInfoService) {

	@ApiOperation(value = "형상관리 접속 정보 리스트")
	@GetMapping("/list")
	fun getScmInfoList(): DeployResponse<*> {
		return scmInfoService.getSCMInfoList()
	}

	@ApiOperation(value = "형상관리 접속 정보 추가")
	@PostMapping("/add")
	fun postGithubApi(@RequestBody scmInfoVo: ScmInfoVo): DeployResponse<*> {
		return scmInfoService.addSCMInfo(scmInfoVo)
	}

	@ApiOperation(value = "형상관리 접속 정보 수정")
	@PostMapping("/modify")
	fun postModifyGithubApi(@RequestBody scmInfoVo: ScmInfoVo): DeployResponse<*> {
		return scmInfoService.modifySCMInfo(scmInfoVo)
	}

	@ApiOperation(value = "형상관리 접속 정보 삭제")
	@PostMapping("/delete")
	fun deleteGithubApi(@RequestBody repoAlias: String): DeployResponse<*> {
		return scmInfoService.deleteSCMInfo(repoAlias)
	}
}