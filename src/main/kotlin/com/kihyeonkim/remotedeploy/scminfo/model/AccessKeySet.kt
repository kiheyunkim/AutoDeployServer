package com.kihyeonkim.remotedeploy.scminfo.model

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-08
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
data class AccessKeySet(
	val userName: String? = null,
	val personalAccessToken: String? = null,
	val scmInfoAlias: String? = null
)
