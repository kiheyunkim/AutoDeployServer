package com.kihyeonkim.remotedeploy.apis.github.model

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-21
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
data class SshConfigModel(
	var host: String,
	var hostName: String,
	var identityFileName: String
)
