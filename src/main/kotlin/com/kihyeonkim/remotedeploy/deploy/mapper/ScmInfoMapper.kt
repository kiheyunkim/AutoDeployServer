package com.kihyeonkim.remotedeploy.deploy.mapper

import com.kihyeonkim.remotedeploy.deploy.model.ScmInfoModel
import com.kihyeonkim.remotedeploy.deploy.vo.ScmInfoVo
import org.apache.ibatis.annotations.Mapper

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-25
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Mapper
interface ScmInfoMapper {
	fun selectScmInfoList(): List<ScmInfoModel>
	fun insertScmInfo(scmInfoModel: ScmInfoModel)
	fun modifyScmInfoList(scmInfoVo: ScmInfoVo)
	fun deleteScmInfo(repoAlias: String)
	fun selectRepoAliasExist(repoAlias: String): Boolean
}