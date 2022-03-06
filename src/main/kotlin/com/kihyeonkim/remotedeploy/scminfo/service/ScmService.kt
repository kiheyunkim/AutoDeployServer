package com.kihyeonkim.remotedeploy.scminfo.service

import com.kihyeonkim.remotedeploy.apis.github.api.GithubApi
import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.scminfo.mapper.AccessKeyMapper
import com.kihyeonkim.remotedeploy.scminfo.mapper.ScmMapper
import com.kihyeonkim.remotedeploy.scminfo.model.Scm
import com.kihyeonkim.remotedeploy.scminfo.model.ScmInfo
import org.apache.commons.lang.StringUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-25
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Service
class ScmService(
	private val scmMapper: ScmMapper,
	private val accessKeyMapper: AccessKeyMapper,
	private val githubApi: GithubApi
) {
	fun getSCMInfoList(): DeployResponse<List<Scm>> {
		return DeployResponse(scmMapper.selectScmInfoList())
	}

	@Transactional
	fun addSCMInfo(scmInfo: ScmInfo): DeployResponse<Boolean> {
		val scm = scmInfo.scm

		if (scm.scmType == null ||
			StringUtils.isBlank(scm.userName) ||
			StringUtils.isBlank(scm.scmInfoAlias) ||
			StringUtils.isBlank(scm.personalAccessToken)
		) {
			return DeployResponse(false, null, "scm 정보 값이 누락되었습니다")
		}

		if (scmMapper.selectScmInfoAliasExist(scm.scmInfoAlias!!)) {
			return DeployResponse(false, null, "이미 존재하는 repoAlias입니다.")
		}

		val validationResult = githubApi.checkApiKeyValidation(scm.personalAccessToken!!)
		if (!validationResult) {
			return DeployResponse(false, null, "잘못된 api 키 입니다.")
		}

		val accessKeySet = scmInfo.accessKeySet
		if (accessKeySet != null) {
			if (StringUtils.isBlank(accessKeySet.userName) ||
				StringUtils.isBlank(accessKeySet.personalAccessToken) ||
				StringUtils.isBlank(accessKeySet.scmInfoAlias)
			) {
				return DeployResponse(false, null, "레포 접근 키셋 값이 누락되었습니다")
			}

			accessKeyMapper.insertAccessKey(accessKeySet)
		}

		scmMapper.insertScmInfo(scm)

		return DeployResponse(true)
	}

	@Transactional
	fun modifySCMInfo(scmInfo: ScmInfo): DeployResponse<*> {
		val scm = scmInfo.scm

		if (StringUtils.isBlank(scm.scmInfoAlias)) {
			return DeployResponse(false, null, "repoAlias값이 비었습니다.")
		}

		if (StringUtils.isNotBlank(scm.personalAccessToken)) {
			val validationResult = githubApi.checkApiKeyValidation(scm.personalAccessToken!!)
			if (!validationResult) {
				return DeployResponse(false, null, "잘못된 api 키 입니다.")
			}
		}

		val accessKeySet = scmInfo.accessKeySet
		if (accessKeySet != null) {
			if (StringUtils.isBlank(accessKeySet.scmInfoAlias)) {
				return DeployResponse(false, null, "레포 접근 키셋 값이 누락되었습니다")
			}

			accessKeyMapper.updateAccessKey(accessKeySet)
		}

		scmMapper.updateScmInfo(scm)

		return DeployResponse(true)
	}

	@Transactional
	fun deleteSCMInfo(repoAlias: String): DeployResponse<*> {
		scmMapper.deleteScmInfo(repoAlias)

		accessKeyMapper.deleteAccessKey(repoAlias)

		return DeployResponse(true)
	}
}