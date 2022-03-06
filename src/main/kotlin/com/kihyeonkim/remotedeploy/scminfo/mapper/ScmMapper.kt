package com.kihyeonkim.remotedeploy.scminfo.mapper

import com.kihyeonkim.remotedeploy.scminfo.model.Scm
import org.apache.ibatis.annotations.Mapper

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-25
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Mapper
interface ScmMapper {
	fun selectScmInfoList(): List<Scm>

	fun insertScmInfo(scm: Scm)

	fun updateScmInfo(scm: Scm)

	fun deleteScmInfo(scmInfoAlias: String)

	fun selectScmInfoAliasExist(scmInfoAlias: String): Boolean
}