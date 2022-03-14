package com.kihyeonkim.remotedeploy.jenkins.service

import com.kihyeonkim.remotedeploy.common.response.DeployResponse
import com.kihyeonkim.remotedeploy.jenkins.mapper.JenkinsMapper
import com.kihyeonkim.remotedeploy.jenkins.model.Jenkins
import org.apache.commons.lang.StringUtils
import org.springframework.stereotype.Service

@Service
class JenkinsService(private val jenkinsMapper: JenkinsMapper) {

	private val pageCount: Int = 10

	fun getJenkinsList(page: Int): DeployResponse<List<Jenkins>?> {
		if (page < 0) {
			return DeployResponse(null, null, "잘못된 페이지 입니다.")
		}

		return DeployResponse(jenkinsMapper.selectJenkinsList(pageCount, (page - 1) * pageCount))
	}


	fun getJenkinsInfoDetailWithoutPassword(jenkinsId: String): DeployResponse<Jenkins> {
		return DeployResponse(jenkinsMapper.selectJenkinsDetail(jenkinsId))
	}

	fun getJenkinsInfoDetailWithPassword(jenkinsId: String): DeployResponse<Jenkins> {
		return DeployResponse(jenkinsMapper.selectJenkinsDetail(jenkinsId))
	}

	fun putJenkinsInfo(jenkins: Jenkins): DeployResponse<Boolean?> {
		if (StringUtils.isEmpty(jenkins.jenkinsUserId) ||
			StringUtils.isEmpty(jenkins.jenkinsPasswd) ||
			StringUtils.isEmpty(jenkins.jenkinsIpAddress) ||
			StringUtils.isEmpty(jenkins.jenkinsPort)
		) {
			return DeployResponse(null, null, "필요 값이 누락되었습니다")
		}

		val insertResult = jenkinsMapper.insertJenkinsInfo(jenkins)

		return DeployResponse(insertResult == 1)
	}

	fun deleteJenkinsInfo(jenkinsId: String): DeployResponse<Boolean> {
		val deleteResult = jenkinsMapper.deleteJenkinsInfo(jenkinsId)

		return DeployResponse(deleteResult == 1)
	}
}