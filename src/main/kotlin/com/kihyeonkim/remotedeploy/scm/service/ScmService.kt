package com.kihyeonkim.remotedeploy.scm.service

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.scm.enumeration.ScmType
import com.kihyeonkim.remotedeploy.scm.mapper.ScmInfoMapper
import com.kihyeonkim.remotedeploy.scm.model.ScmInfoModel
import com.kihyeonkim.remotedeploy.scm.vo.ScmInfoVo
import com.kihyeonkim.remotedeploy.apis.github.api.GithubApi
import org.apache.commons.lang.StringUtils
import org.springframework.stereotype.Service

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-25
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Service
class ScmService(
	private val scmInfoMapper: ScmInfoMapper,
	private val githubApi: GithubApi
) {
	fun getSCMInfoList(): DeployResponse<List<ScmInfoModel>> {
		return DeployResponse(scmInfoMapper.selectScmInfoList())
	}

	fun addSCMInfo(scmInfoVo: ScmInfoVo): DeployResponse<*> {
		if (StringUtils.isBlank(scmInfoVo.scmType) ||
			StringUtils.isBlank(scmInfoVo.userName) ||
			StringUtils.isBlank(scmInfoVo.repoAlias) ||
			StringUtils.isBlank(scmInfoVo.personalAccessToken)
		) {
			return DeployResponse(false, null, "값이 누락되었습니다")
		}

		if (scmInfoMapper.selectRepoAliasExist(scmInfoVo.repoAlias!!)) {
			return DeployResponse(false, null, "이미 존재하는 repoAlias입니다.")
		}

		val validationResult = githubApi.checkApiKeyValidation(scmInfoVo.personalAccessToken!!)
		if (!validationResult) {
			return DeployResponse(false, null, "잘못된 api 키 입니다.")
		}


		val scmType = try {
			ScmType.valueOf(scmInfoVo.scmType!!)
		} catch (illegalArgumentException: IllegalArgumentException) {
			return DeployResponse(false, null, "잘못된 scm 타입입니다")
		}

		scmInfoMapper.insertScmInfo(
			ScmInfoModel(
				scmType,
				scmInfoVo.repoAlias!!,
				scmInfoVo.userName!!,
				scmInfoVo.personalAccessToken!!
			)
		)

		return DeployResponse(true)
	}

	fun modifySCMInfo(scmInfoVo: ScmInfoVo): DeployResponse<*> {
		if (StringUtils.isBlank(scmInfoVo.repoAlias)) {
			return DeployResponse(false, null, "repoAlias값이 비었습니다.")
		}

		if (StringUtils.isNotBlank(scmInfoVo.personalAccessToken)) {
			val validationResult = githubApi.checkApiKeyValidation(scmInfoVo.personalAccessToken!!)
			if (!validationResult) {
				return DeployResponse(false, null, "잘못된 api 키 입니다.")
			}
		}

		var scmType: ScmType? = null
		if (StringUtils.isNotBlank(scmInfoVo.scmType)) {
			scmType = try {
				ScmType.valueOf(scmInfoVo.scmType!!)
			} catch (illegalArgumentException: IllegalArgumentException) {
				return DeployResponse(false, null, "잘못된 scm 타입입니다")
			}
		}

		scmInfoMapper.updateScmInfo(scmInfoVo.repoAlias!!, scmType, scmInfoVo.userName, scmInfoVo.personalAccessToken)

		return DeployResponse(true)
	}

	fun deleteSCMInfo(repoAlias: String): DeployResponse<*> {
		scmInfoMapper.deleteScmInfo(repoAlias)

		return DeployResponse(true)
	}
}