package com.kihyeonkim.remotedeploy.deploy.service

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.deploy.mapper.ScmInfoMapper
import com.kihyeonkim.remotedeploy.deploy.model.ScmModel
import org.springframework.stereotype.Service

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-25
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Service
class SCMService(private val scmInfoMapper: ScmInfoMapper) {
	fun getSCMInfoList(): DeployResponse<List<ScmModel>> {
		return DeployResponse(scmInfoMapper.selectScmInfoList())
	}

	fun addSCMInfoList(scmModel: ScmModel) {
		//ToDo: 검증로직
		scmInfoMapper.insertScmInfo(scmModel)
	}

	fun modifySCMInfo(scmModel: ScmModel) {
		scmInfoMapper.modifyScmInfoList(scmModel)
	}

	fun deleteSCMInfo(repoAlias: String) {
		scmInfoMapper.deleteScmInfo(repoAlias)
	}
}