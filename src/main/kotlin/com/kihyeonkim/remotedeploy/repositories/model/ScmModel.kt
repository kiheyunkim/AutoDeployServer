package com.kihyeonkim.remotedeploy.repositories.model

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-11
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
data class ScmModel(
	var scmInfoAlias: String?,
	var scmAlias: String,
	var repositorySshUrl: String,
	var isPrivate: Boolean
)
