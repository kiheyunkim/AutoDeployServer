package com.kihyeonkim.remotedeploy.jenkins.mapper

import com.kihyeonkim.remotedeploy.jenkins.model.Jenkins
import org.apache.ibatis.annotations.Mapper

@Mapper
interface JenkinsMapper {
	fun selectJenkinsList(pageCount: Int, offSet: Int): List<Jenkins>

	fun selectJenkinsDetail(jenkinsId: String): Jenkins

	fun insertJenkinsInfo(jenkins: Jenkins): Int

	fun deleteJenkinsInfo(jenkinsId: String): Int
}