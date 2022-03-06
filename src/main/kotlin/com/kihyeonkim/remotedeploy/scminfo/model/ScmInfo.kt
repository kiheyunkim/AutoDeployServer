package com.kihyeonkim.remotedeploy.scminfo.model

data class ScmInfo(
	val scm: Scm,
	val accessKeySet: AccessKeySet? = null
)
