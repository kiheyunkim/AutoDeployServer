package com.kihyeonkim.remotedeploy.remoteserver.controller

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.remoteserver.service.RemoteServerService
import com.kihyeonkim.remotedeploy.remoteserver.model.RemoteServerInfo
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-27
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Controller
@Api("원격 서버 관리")
@RequestMapping("/remoteServer")
class RemoteServerController(private val remoteServerService: RemoteServerService) {

	@ApiOperation(value = "원격 서버 목록 반환")
	@GetMapping("/list")
	fun getServerInfoList(): DeployResponse<*> {
		return remoteServerService.getRemoteServerList()
	}

	@ApiOperation(value = "원격 서버 상세 정보 반환")
	@GetMapping("/detail")
	fun getServerInfoDetail(remoteServerAlias: String): DeployResponse<*> {
		return remoteServerService.getRemoteServerConnectionInfo(remoteServerAlias)
	}

	@ApiOperation(value = "원격 서버 상세 정보 추가")
	@PostMapping("/add")
	fun postServerInfo(remoteServerInfoVo: RemoteServerInfo): DeployResponse<*> {
		return remoteServerService.addRemoteServeInfo(remoteServerInfoVo)
	}

	@ApiOperation(value = "원격 서버 정보 삭제")
	@DeleteMapping("/delete")
	fun deleteServerInfo(remoteServerAlias: String): DeployResponse<*> {
		return remoteServerService.deleteRemoteServeInfo(remoteServerAlias)
	}

	@ApiOperation(value = "원격 서버 정보 수정")
	@PostMapping("/modify")
	fun modifyServerInfo(remoteServerInfo: RemoteServerInfo): DeployResponse<*> {
		return remoteServerService.modifyRemoteServerInfo(remoteServerInfo)
	}
}