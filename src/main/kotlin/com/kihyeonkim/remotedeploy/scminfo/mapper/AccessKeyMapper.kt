package com.kihyeonkim.remotedeploy.scminfo.mapper

import com.kihyeonkim.remotedeploy.scminfo.model.AccessKeySet
import org.apache.ibatis.annotations.Mapper

@Mapper
interface AccessKeyMapper {
	fun selectAccessKey(scmInfoAlias: String): AccessKeySet

	fun insertAccessKey(accessKeySet: AccessKeySet)

	fun updateAccessKey(accessKeySet: AccessKeySet)

	fun deleteAccessKey(scmInfoAlias: String)
}