package com.kihyeonkim.remotedeploy.github.model

import com.google.gson.annotations.SerializedName

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-16
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
data class RepositoryInfo(
	@SerializedName("full_name") val fullName: String,
	@SerializedName("html_url") val htmlUrl: String,
	@SerializedName("ssh_url") val sshUrl: String
)
