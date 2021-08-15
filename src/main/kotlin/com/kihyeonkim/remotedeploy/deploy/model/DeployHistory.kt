package com.kihyeonkim.remotedeploy.deploy.model

import com.kihyeonkim.remotedeploy.deploy.enumeration.DeployState
import com.kihyeonkim.remotedeploy.deploy.enumeration.DeployStage
import org.apache.ibatis.type.Alias
import java.time.LocalDateTime

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-13
 * Github : http://github.com/kiheyunkim
 * Comment :
 */
@Alias("deployHistory")
data class DeployHistory(
	var id: Int,
	var deployStage: DeployStage,
	var deployState: DeployState,
	var scenarioName: String,
	var buildTarget: String,
	var deployFrom: String,
	var deployTo: String,
	var executionDate: LocalDateTime,
)
