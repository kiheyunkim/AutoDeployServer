package com.kihyeonkim.remotedeploy.scminfo.mapper

import com.kihyeonkim.remotedeploy.scminfo.enumeration.ScmType
import com.kihyeonkim.remotedeploy.scminfo.model.ScmInfoModel
import com.kihyeonkim.remotedeploy.deploy.model.GithubKeySetModel
import com.kihyeonkim.remotedeploy.scminfo.model.ScmInfoListModel
import org.apache.ibatis.annotations.Mapper

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-25
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Mapper
interface ScmInfoMapper {
	fun selectScmInfoList(): List<ScmInfoListModel>
	fun selectScmKeySet(scmInfoAlias: String): GithubKeySetModel?
	fun insertScmInfo(scmInfoModel: ScmInfoModel)
	fun updateScmInfo(scmInfoAlias: String, scmType: ScmType?, userName: String?, personalAccessToken: String?)
	fun deleteScmInfo(scmInfoAlias: String)
	fun selectRepoAliasExist(scmInfoAlias: String): Boolean
}