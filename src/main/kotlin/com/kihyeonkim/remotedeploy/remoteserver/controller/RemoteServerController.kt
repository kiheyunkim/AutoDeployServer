package com.kihyeonkim.remotedeploy.remoteserver.controller

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.remoteserver.service.RemoteServerService
import com.kihyeonkim.remotedeploy.remoteserver.vo.RemoteServerInfoVo
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
@RequestMapping("/server")
class RemoteServerController(private val remoteServerService: RemoteServerService) {
	@GetMapping("/list")
	fun getServerInfoList(): DeployResponse<*> {
		return remoteServerService.getRemoteServerList()
	}

	@PostMapping("/add")
	fun postServerInfo(remoteServerInfoVo: RemoteServerInfoVo): DeployResponse<*> {
		return remoteServerService.addRemoteServeInfo(remoteServerInfoVo)
	}

	@DeleteMapping("/delete")
	fun deleteServerInfo(remoteServerAlias: String): DeployResponse<*> {
		return remoteServerService.deleteRemoteServeInfo(remoteServerAlias)
	}
}