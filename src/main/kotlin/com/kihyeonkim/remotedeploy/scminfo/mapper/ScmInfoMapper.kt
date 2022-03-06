package com.kihyeonkim.remotedeploy.scminfo.mapper

import com.kihyeonkim.remotedeploy.scminfo.model.ScmInfo
import org.apache.ibatis.annotations.Mapper

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-25
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Mapper
interface ScmInfoMapper {
	fun selectScmInfoList(): List<ScmInfo>

	fun insertScmInfo(scmInfo: ScmInfo)

	fun updateScmInfo(scmInfo: ScmInfo)

	fun deleteScmInfo(scmInfoAlias: String)

	fun selectScmInfoAliasExist(scmInfoAlias: String): Boolean
}