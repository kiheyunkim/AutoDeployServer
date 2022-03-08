package com.kihyeonkim.remotedeploy.repositories.service

import com.kihyeonkim.remotedeploy.apis.github.api.GithubApi
import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.repositories.mapper.RepositoryMapper
import com.kihyeonkim.remotedeploy.repositories.model.Repository
import org.apache.commons.lang.StringUtils
import org.springframework.stereotype.Service

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-09
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Service
class RepositoryService(
	private val repositoryMapper: RepositoryMapper,
	private val githubApi: GithubApi
) {
	private val pageCount: Int = 10

	fun getRepositoryList(page: Int): DeployResponse<List<Repository>?> {
		if (page < 0) {
			return DeployResponse(null, null, "잘못된 페이지 입니다.")
		}

		return DeployResponse(repositoryMapper.selectRepositoryInfoList(pageCount, (page - 1) * pageCount))
	}

	fun getRepositoryDetail(repositoryAlias: String): DeployResponse<Repository> {

		return DeployResponse(repositoryMapper.selectRepositoryDetail(repositoryAlias))
	}

	fun addRepositoryInfo(repository: Repository): DeployResponse<Boolean?> {
		if (StringUtils.isEmpty(repository.repositoryAlias) ||
			StringUtils.isEmpty(repository.scmAlias) ||
			StringUtils.isEmpty(repository.repositorySshUrl) ||
			repository.isPrivate
		) {
			return DeployResponse(null, null, "필요 값이 누락되었습니다")
		}

		val insertResult = repositoryMapper.insertRepositoryInfo(repository)

		return DeployResponse(insertResult == 1, null, null)
	}

	fun updateRepositoryInfo(repository: Repository): DeployResponse<Boolean?> {
		if (StringUtils.isEmpty(repository.repositoryAlias) ||
			StringUtils.isEmpty(repository.scmAlias)
		) {
			return DeployResponse(null, null, "필요 값이 누락되었습니다")
		}

		val updateResult = repositoryMapper.updateRepositoryInfo(repository)

		return DeployResponse(updateResult == 1, null, null)
	}

	fun deleteRepositoryInfo(repositoryAlias: String): DeployResponse<Boolean?> {
		if (StringUtils.isEmpty(repositoryAlias)) {
			return DeployResponse(null, null, "필요 값이 누락되었습니다")
		}

		val deleteResult = repositoryMapper.deleteRepositoryInfo(repositoryAlias)

		return DeployResponse(deleteResult == 1, null, null)
	}
}