package com.kihyeonkim.remotedeploy.deploy.model

import com.kihyeonkim.remotedeploy.scm.enumeration.ScmType

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-26
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
data class RepoAliasModel(
	var scmType: ScmType,
	var repoAlias: String,
	var userName: String
)
