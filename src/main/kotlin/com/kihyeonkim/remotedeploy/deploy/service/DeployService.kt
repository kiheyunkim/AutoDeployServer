package com.kihyeonkim.remotedeploy.deploy.service

import com.kihyeonkim.remotedeploy.apis.github.api.GithubApi
import com.kihyeonkim.remotedeploy.apis.jenkins.api.JenkinsApi
import com.kihyeonkim.remotedeploy.apis.jenkins.enumeration.BuildType
import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import org.springframework.stereotype.Service

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-13
 * Github : http://github.com/kiheyunkim
 * Comment :
 */
@Service
class DeployService(
	private val jenkinsApi: JenkinsApi,
	private val githubApi: GithubApi
) {
	private val gitUrlTemplate: (String, String, String) -> String = { identityFileName, username, repositoryName ->
		"git@$identityFileName:$username/$repositoryName.git"
	}

	fun createDeployJob(repoAlias: String, repositoryName: String, buildType: BuildType): DeployResponse<*> {
		val githubKeySet = null
			?: return DeployResponse(false, null, "등록되지 않은 repoAlias")

		val createResult = jenkinsApi.createJenkinsJob(
			"DEPLOY_${repoAlias.uppercase()}_${repositoryName.uppercase()}",
			gitUrlTemplate(
				"${repoAlias.uppercase()}_${repositoryName.uppercase()}",
				githubKeySet,
				repositoryName
			),
			buildType
		)

		if (!createResult.value()) {
			return DeployResponse(false, null, "Jenkins Job 생성 실패")
		}

		if (!githubApi.addDeployKeyAndSSHKey(githubKeySet, repoAlias, repositoryName)) {
			return DeployResponse(false, null, "SSH 키 등록 또는 생성 실패")
		}

		return DeployResponse(createResult.value(), null, null)
	}

	fun deleteJenkinsJob(repoAlias: String, repositoryName: String): DeployResponse<*> {
		val requestStatus = jenkinsApi.deleteJenkinsJob("DEPLOY_${repoAlias.uppercase()}_${repositoryName.uppercase()}")

		if(!githubApi.removeSSHKey(repoAlias, repositoryName)){
			return DeployResponse(requestStatus.value(), null, "sshKey 삭제 실패")
		}

		if (!requestStatus.value()) {
			return DeployResponse(requestStatus.value(), null, "Jenkins 삭제 실패")
		}

		return DeployResponse(true, null, null)
	}


	fun startJenkinsJob(jobName: String, params: Map<String, List<String>>?): DeployResponse<*> {
		return DeployResponse(jenkinsApi.startJenkinsJob(jobName, params))
	}

	fun getDeployProgress(jobName: String, jobNumber: Int, start: Int?): DeployResponse<*> {
		return DeployResponse(jenkinsApi.getJenkinsLog(jobName, jobNumber, start))
	}


}