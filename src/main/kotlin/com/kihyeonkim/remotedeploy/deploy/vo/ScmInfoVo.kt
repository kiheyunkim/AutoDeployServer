package com.kihyeonkim.remotedeploy.deploy.vo

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-25
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
data class ScmInfoVo(
	var scmType: String?,
	var repoAlias: String?,
	var userName: String?,
	var personalAccessToken: String?,
)
