package com.kihyeonkim.remotedeploy.repositories.vo

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-10
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
data class ScmVo(
	var scmInfoAlias: String?,
	var scmAlias: String,
	var repositoryName: String,
	var isPrivate: Boolean
)
