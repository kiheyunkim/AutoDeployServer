package com.kihyeonkim.remotedeploy.scm.service

import com.kihyeonkim.remotedeploy.apis.github.api.GithubApi
import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.scminfo.mapper.ScmInfoMapper
import org.springframework.stereotype.Service

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-09
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Service
class ScmService(
	private val scmInfoMapper: ScmInfoMapper,
	private val githubApi: GithubApi
) {

	fun getRepositoryList(scmInfoAlias: String): DeployResponse<*> {
		val scmKeySet = scmInfoMapper.selectScmKeySet(scmInfoAlias)

		val result = githubApi.getRepositoryList(scmKeySet.accessToken)

		return DeployResponse(result)
	}
}