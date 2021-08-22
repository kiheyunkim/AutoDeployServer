package com.kihyeonkim.remotedeploy.common.model

import com.kihyeonkim.remotedeploy.common.enumeration.SshConfigMessageType
import java.io.Serializable

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-21
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
data class SshConfigMessageModel(
	var sshConfigMessageType: SshConfigMessageType,
	var host: String,
	var hostName: String,
	var identityFileName: String
) : Serializable
