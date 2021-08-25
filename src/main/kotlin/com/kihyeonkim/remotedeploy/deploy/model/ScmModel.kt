package com.kihyeonkim.remotedeploy.deploy.model

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-25
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
data class ScmModel(
	var scmType: String,
	var repoAlias: String,
	var userName: String,
	var apiKey: String,
)
