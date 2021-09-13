package com.kihyeonkim.remotedeploy.scminfo.model

import com.kihyeonkim.remotedeploy.scminfo.enumeration.ScmType

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-26
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
data class ScmInfoModel(
	var scmType: ScmType,
	var scmInfoAlias: String,
	var userName: String,
	var personalAccessToken: String,
)
