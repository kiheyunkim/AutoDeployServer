package com.kihyeonkim.remotedeploy.jenkins.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Jenkins(
	val jenkinsId: String,
	val jenkinsUserId: String,
	val jenkinsPasswd: String,
	val jenkinsIpAddress: String,
	val jenkinsPort: String
)
