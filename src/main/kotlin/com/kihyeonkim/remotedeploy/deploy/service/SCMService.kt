package com.kihyeonkim.remotedeploy.deploy.service

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.deploy.mapper.ScmInfoMapper
import com.kihyeonkim.remotedeploy.deploy.model.ScmInfoModel
import com.kihyeonkim.remotedeploy.deploy.vo.ScmInfoVo
import com.kihyeonkim.remotedeploy.github.api.GithubApi
import org.apache.commons.lang.StringUtils
import org.springframework.stereotype.Service

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-25
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Service
class SCMService(
	private val scmInfoMapper: ScmInfoMapper,
	private val githubApi: GithubApi
) {
	fun getSCMInfoList(): DeployResponse<List<ScmInfoModel>> {
		return DeployResponse(scmInfoMapper.selectScmInfoList())
	}

	fun addSCMInfoList(scmInfoVo: ScmInfoVo): DeployResponse<*> {
		if (StringUtils.isBlank(scmInfoVo.scmType) ||
			StringUtils.isBlank(scmInfoVo.userName) ||
			StringUtils.isBlank(scmInfoVo.repoAlias) ||
			StringUtils.isBlank(scmInfoVo.apiKey)
		) {
			return DeployResponse(false, null, "값이 누락되었습니다")
		}

		if (scmInfoMapper.selectRepoAliasExist(scmInfoVo.repoAlias!!)) {
			return DeployResponse(false, null, "이미 존재하는 repoAlias입니다.")
		}

		val validationResult = githubApi.checkApiKeyValidation(scmInfoVo.apiKey!!)
		if (!validationResult) {
			return DeployResponse(false, null, "잘못된 api 키 입니다.")
		}

		scmInfoMapper.insertScmInfo(
			ScmInfoModel(
				scmInfoVo.scmType!!,
				scmInfoVo.repoAlias!!,
				scmInfoVo.userName!!,
				scmInfoVo.apiKey!!
			)
		)

		return DeployResponse(true)
	}

	fun modifySCMInfo(scmInfoVo: ScmInfoVo): DeployResponse<*> {
		if (StringUtils.isBlank(scmInfoVo.repoAlias)) {
			return DeployResponse(false, null, "repoAlias값이 비었습니다.")
		}

		if (StringUtils.isNotBlank(scmInfoVo.apiKey)) {
			val validationResult = githubApi.checkApiKeyValidation(scmInfoVo.apiKey!!)
			if (!validationResult) {
				return DeployResponse(false, null, "잘못된 api 키 입니다.")
			}
		}

		scmInfoMapper.modifyScmInfoList(scmInfoVo)

		return DeployResponse(true)
	}

	fun deleteSCMInfo(repoAlias: String): DeployResponse<*> {
		scmInfoMapper.deleteScmInfo(repoAlias)

		return DeployResponse(true)
	}
}