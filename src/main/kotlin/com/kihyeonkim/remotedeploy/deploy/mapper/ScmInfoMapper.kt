package com.kihyeonkim.remotedeploy.deploy.mapper

import com.kihyeonkim.remotedeploy.deploy.model.ScmModel
import org.apache.ibatis.annotations.Mapper

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-25
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Mapper
interface ScmInfoMapper {
	fun selectScmInfoList(): List<ScmModel>
	fun insertScmInfo(scmModel: ScmModel)
	fun modifyScmInfoList(scmModel: ScmModel)
	fun deleteScmInfo(repoAlias: String)
}