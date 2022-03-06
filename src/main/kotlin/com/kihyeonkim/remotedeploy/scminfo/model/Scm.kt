package com.kihyeonkim.remotedeploy.scminfo.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.kihyeonkim.remotedeploy.scminfo.enumeration.ScmType

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-26
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Scm(
	val scmType: ScmType? = null,
	val scmInfoAlias: String? = null,
	val userName: String? = null,
	val personalAccessToken: String? = null
)
