package com.kihyeonkim.remotedeploy.deploy.service

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.deploy.mapper.DeployHistoryMapper
import com.kihyeonkim.remotedeploy.jenkins.api.JenkinsApi
import com.kihyeonkim.remotedeploy.jenkins.enumeration.BuildType
import org.springframework.stereotype.Service

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-13
 * Github : http://github.com/kiheyunkim
 * Comment :
 */
@Service
class DeployService(private val jenkinsApi: JenkinsApi, private val deployHistoryMapper: DeployHistoryMapper) {
	fun createJenkinsJob(jobName: String, gitUrl: String, buildType: BuildType): DeployResponse<*> {
		val createResult = jenkinsApi.createJenkinsJob(jobName, gitUrl, buildType)

		return if (createResult.value()) {
			DeployResponse(createResult.value())
		} else {
			DeployResponse(createResult.value(), null, "Jenkins Job 생성 실패")
		}
	}

	fun startJenkinsJob(jobName: String, params: Map<String, List<String>>?): DeployResponse<*> {
		return DeployResponse(jenkinsApi.startJenkinsJob(jobName, params))
	}

	fun deleteJenkinsJob(jobName: String): DeployResponse<*> {
		val requestStatus = jenkinsApi.deleteJenkinsJob(jobName)

		return if (requestStatus.value()) {
			DeployResponse(requestStatus.value(), null, "Jenkins 삭제 실패")
		} else {
			DeployResponse(requestStatus.value())
		}
	}

	fun getDeployProgress(jobName: String, jobNumber: Int, start: Int?): DeployResponse<*> {
		return DeployResponse(jenkinsApi.getJenkinsLog(jobName, jobNumber, start))
	}


}