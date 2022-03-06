package com.kihyeonkim.remotedeploy.scminfo.service

import com.kihyeonkim.remotedeploy.apis.github.api.GithubApi
import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.scminfo.mapper.ScmInfoMapper
import com.kihyeonkim.remotedeploy.scminfo.model.ScmInfo
import org.apache.commons.lang.StringUtils
import org.springframework.stereotype.Service

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-25
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Service
class ScmInfoService(
	private val scmInfoMapper: ScmInfoMapper,
	private val githubApi: GithubApi
) {
	fun getSCMInfoList(): DeployResponse<List<ScmInfo>> {
		return DeployResponse(scmInfoMapper.selectScmInfoList())
	}

	fun addSCMInfo(scmInfo: ScmInfo): DeployResponse<Boolean> {
		if (scmInfo.scmType == null ||
			StringUtils.isBlank(scmInfo.userName) ||
			StringUtils.isBlank(scmInfo.scmInfoAlias) ||
			StringUtils.isBlank(scmInfo.personalAccessToken)
		) {
			return DeployResponse(false, null, "값이 누락되었습니다")
		}

		if (scmInfoMapper.selectScmInfoAliasExist(scmInfo.scmInfoAlias!!)) {
			return DeployResponse(false, null, "이미 존재하는 repoAlias입니다.")
		}

		val validationResult = githubApi.checkApiKeyValidation(scmInfo.personalAccessToken!!)
		if (!validationResult) {
			return DeployResponse(false, null, "잘못된 api 키 입니다.")
		}

		scmInfoMapper.insertScmInfo(scmInfo)

		return DeployResponse(true)
	}

	fun modifySCMInfo(scmInfo: ScmInfo): DeployResponse<*> {
		if (StringUtils.isBlank(scmInfo.scmInfoAlias)) {
			return DeployResponse(false, null, "repoAlias값이 비었습니다.")
		}

		if (StringUtils.isNotBlank(scmInfo.personalAccessToken)) {
			val validationResult = githubApi.checkApiKeyValidation(scmInfo.personalAccessToken!!)
			if (!validationResult) {
				return DeployResponse(false, null, "잘못된 api 키 입니다.")
			}
		}

		scmInfoMapper.updateScmInfo(scmInfo)

		return DeployResponse(true)
	}

	fun deleteSCMInfo(repoAlias: String): DeployResponse<*> {
		scmInfoMapper.deleteScmInfo(repoAlias)

		return DeployResponse(true)
	}
}