package com.kihyeonkim.remotedeploy.repositories.service

import com.kihyeonkim.remotedeploy.apis.github.api.GithubApi
import org.springframework.stereotype.Service

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-09
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Service
class RepositoryService(
	private val scmInfoMapper: com.kihyeonkim.remotedeploy.scminfo.mapper.ScmMapper,
	private val githubApi: GithubApi
) {
	private val pageCount: Int = 10
/*
	fun getRepositoryList(scmInfoAlias: String): DeployResponse<*> {
		val scmKeySet = scmInfoMapper.selectScmKeySet(scmInfoAlias)

		return DeployResponse(githubApi.getRepositoryList(scmKeySet.accessToken))
	}

	fun getScmList(page: Int): DeployResponse<*> {
		if (page < 0) {
			return DeployResponse(null, null, "잘못된 페이지 입니다.")
		}

		return DeployResponse(scmMapper.selectScmList((page - 1) * pageCount, pageCount))
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
	}*/
}