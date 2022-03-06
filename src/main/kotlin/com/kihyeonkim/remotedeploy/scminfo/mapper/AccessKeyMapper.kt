package com.kihyeonkim.remotedeploy.scminfo.mapper

import com.kihyeonkim.remotedeploy.scminfo.model.AccessKeySet

interface AccessKeyMapper {
	fun selectAccessKey(scmInfoAlias: String): AccessKeySet

	fun insertAccessKey(accessKeySet: AccessKeySet)

	fun updateAccessKey(accessKeySet: AccessKeySet)

	fun deleteAccessKey(scmInfoAlias: String)
}