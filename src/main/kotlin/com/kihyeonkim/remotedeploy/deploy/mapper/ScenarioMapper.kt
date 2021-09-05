package com.kihyeonkim.remotedeploy.deploy.mapper

import com.kihyeonkim.remotedeploy.deploy.enumeration.DeployStage
import org.apache.ibatis.annotations.Mapper

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-01
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Mapper
interface ScenarioMapper {
	fun selectScenarioList()
	fun insertScenario(scmAlias: String, remoteServerAlias: String, deployStage: DeployStage)
	fun modifyScenario(scenarioId: Int, scmAlias: String, remoteServerAlias: String)
	fun deleteScenario(scenarioId: Int)
}