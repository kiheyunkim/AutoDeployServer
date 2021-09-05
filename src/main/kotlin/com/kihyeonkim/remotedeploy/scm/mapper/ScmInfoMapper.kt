package com.kihyeonkim.remotedeploy.scm.mapper

import com.kihyeonkim.remotedeploy.scm.enumeration.ScmType
import com.kihyeonkim.remotedeploy.scm.model.ScmInfoModel
import com.kihyeonkim.remotedeploy.deploy.model.GithubKeySetModel
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
	fun selectScmKeySet(repoAlias: String): GithubKeySetModel?
	fun insertScmInfo(scmInfoModel: ScmInfoModel)
	fun updateScmInfo(repoAlias: String, scmType: ScmType?, userName: String?, personalAccessToken: String?)
	fun deleteScmInfo(repoAlias: String)
	fun selectRepoAliasExist(repoAlias: String): Boolean
}