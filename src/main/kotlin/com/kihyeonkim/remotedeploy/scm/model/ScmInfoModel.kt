package com.kihyeonkim.remotedeploy.scm.model

import com.kihyeonkim.remotedeploy.deploy.enumeration.ScmType

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-26
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
data class ScmInfoModel(
	var scmType: ScmType,
	var repoAlias: String,
	var userName: String,
	var personalAccessToken: String,
)
