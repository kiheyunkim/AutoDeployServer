package com.kihyeonkim.remotedeploy.remoteserver.mdoel

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-27
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
data class RemoteServerModel(
	var remoteServerAlias: String,
	var remoteIp: String,
	var publicKey: String
)
