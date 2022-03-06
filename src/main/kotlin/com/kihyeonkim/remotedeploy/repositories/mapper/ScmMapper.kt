package com.kihyeonkim.remotedeploy.repositories.mapper

import com.kihyeonkim.remotedeploy.repositories.model.ScmModel
import org.apache.ibatis.annotations.Mapper

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-11
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Mapper
interface ScmMapper {
	fun selectScmList(offset: Int, pageCount: Int)
	fun insertScm(scmModel: ScmModel)
	fun deleteScm(scmAlias: String)
	fun selectScmAliasExist(scmAlias: String): Boolean
}