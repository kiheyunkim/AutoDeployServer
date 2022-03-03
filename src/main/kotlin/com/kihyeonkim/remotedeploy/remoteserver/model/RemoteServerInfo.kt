package com.kihyeonkim.remotedeploy.remoteserver.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-28
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@JsonInclude(Include.NON_NULL)
data class RemoteServerInfo(
	val remoteServerAlias: String?,
	val remoteIp: String?,
	val publicKey: String?
)
