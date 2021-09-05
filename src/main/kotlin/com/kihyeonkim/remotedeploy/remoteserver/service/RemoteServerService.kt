package com.kihyeonkim.remotedeploy.remoteserver.service

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.remoteserver.mapper.RemoteServerMapper
import com.kihyeonkim.remotedeploy.remoteserver.mdoel.RemoteServerModel
import com.kihyeonkim.remotedeploy.remoteserver.vo.RemoteServerInfoVo
import org.apache.commons.lang.StringUtils
import org.springframework.stereotype.Service
import java.util.regex.Pattern

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-28
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Service
class RemoteServerService(private val remoteServerMapper: RemoteServerMapper) {
	fun getRemoteServerList(): DeployResponse<*> {

		return DeployResponse(remoteServerMapper.selectRemoteServerInfoList())
	}

	fun getRemoteServerConnectionInfo(remoteServerAlias: String): DeployResponse<*> {
		val queryResult = remoteServerMapper.selectRemoteServerConnectionInfo(remoteServerAlias)

		return if (queryResult == null) {
			DeployResponse(queryResult)
		} else {
			DeployResponse(false, null, "존재하지 않는 remoteServerAlias입니다")
		}
	}

	fun addRemoteServeInfo(remoteServerInfoVo: RemoteServerInfoVo): DeployResponse<*> {
		if (StringUtils.isEmpty(remoteServerInfoVo.remoteServerAlias) ||
			StringUtils.isEmpty(remoteServerInfoVo.remoteIp) ||
			StringUtils.isEmpty(remoteServerInfoVo.publicKey)
		) {
			return DeployResponse(null, null, "값이 누락되었습니다")
		}


		if (Pattern.compile("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\$\n")
				.matcher(remoteServerInfoVo.remoteIp!!).matches()
		) {
			return DeployResponse(null, null, "올바르지 않은 IP주소입니다")
		}

		if (remoteServerMapper.selectRemoteServerAliasExist(remoteServerInfoVo.remoteServerAlias!!)) {
			return DeployResponse(null, null, "이미 존재하는 Alias 입니다")
		}

		remoteServerMapper.insertRemoteServerInfo(
			RemoteServerModel(
				remoteServerInfoVo.remoteServerAlias!!,
				remoteServerInfoVo.remoteIp!!,
				remoteServerInfoVo.publicKey!!
			)
		)

		return DeployResponse(true)
	}

	fun deleteRemoteServeInfo(remoteServerAlias: String): DeployResponse<*> {
		val deleteResult = remoteServerMapper.deleteRemoteServerInfo(remoteServerAlias)
		return if (deleteResult > 0) {
			DeployResponse(true)
		} else {
			DeployResponse(null, null, "삭제할 대상이 없습니다")
		}
	}
}