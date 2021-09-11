package com.kihyeonkim.remotedeploy.scm.service

import com.kihyeonkim.remotedeploy.apis.github.api.GithubApi
import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.scm.mapper.ScmMapper
import com.kihyeonkim.remotedeploy.scm.model.ScmModel
import com.kihyeonkim.remotedeploy.scm.vo.ScmVo
import com.kihyeonkim.remotedeploy.scminfo.mapper.ScmInfoMapper
import org.apache.commons.lang.StringUtils
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-09
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Service
class ScmService(
	private val scmInfoMapper: ScmInfoMapper,
	private val scmMapper: ScmMapper,
	private val githubApi: GithubApi
) {

	fun getKeyAliasList(): DeployResponse<*> {
		return DeployResponse(scmInfoMapper.selectScmInfoAliasList())
	}

	fun getRepositoryList(scmInfoAlias: String): DeployResponse<*> {
		val scmKeySet = scmInfoMapper.selectScmKeySet(scmInfoAlias)

		return DeployResponse(githubApi.getRepositoryList(scmKeySet.accessToken))
	}

	fun getScmList(page: Int): DeployResponse<*> {
		if (page < 0) {
			return DeployResponse(null, null, "잘못된 페이지 입니다.")
		}

		return DeployResponse(scmMapper.selectScmList(page))
	}

	fun addScm(scmVo: ScmVo): DeployResponse<*> {
		if (scmVo.isPrivate) {
			if (StringUtils.isBlank(scmVo.scmInfoAlias) || StringUtils.isBlank(scmVo.scmAlias) || StringUtils.isBlank(
					scmVo.repositoryName
				)
			) {
				return DeployResponse(null, null, "값이 누락됐습니다.")
			}
		} else {
			if (StringUtils.isBlank(scmVo.scmAlias) || StringUtils.isBlank(scmVo.repositoryName)) {
				return DeployResponse(null, null, "값이 누락됐습니다.")
			}
		}

		val scmAliasExist = scmMapper.selectScmAliasExist(scmVo.scmAlias)
		if (scmAliasExist) {
			return DeployResponse(null, null, "중복된 scm ALias입니다.")
		}

		val scmKeySet = scmInfoMapper.selectScmKeySet(scmVo.scmInfoAlias!!)

		val repositorySshUrl = try {
			githubApi.getRepositorySshUrl(scmKeySet.userName, scmVo.repositoryName, scmKeySet.accessToken)
		} catch (restClientException: RestClientException) {
			return DeployResponse(null, null, "repository 정보 조회 실패")
		}

		return DeployResponse(
			scmMapper.insertScm(
				ScmModel(
					scmVo.scmInfoAlias,
					scmVo.scmAlias,
					repositorySshUrl,
					scmVo.isPrivate
				)
			)
		)
	}

	fun deleteScm(scmAlias: String): DeployResponse<*> {
		return DeployResponse(scmMapper.deleteScm(scmAlias))
	}
}