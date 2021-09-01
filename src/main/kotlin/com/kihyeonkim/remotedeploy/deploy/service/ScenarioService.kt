package com.kihyeonkim.remotedeploy.deploy.service

import com.kihyeonkim.remotedeploy.deploy.enumeration.DeployStage
import org.springframework.stereotype.Service

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-29
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Service
class ScenarioService {
	fun getScenarioList(){

	}

	fun addScenario(scmAlias: String, remoteServerAlias:String, deployStage: DeployStage){

	}

	fun deleteScenario(scenarioId: Int){

	}

	fun modifyScenario(scmAlias: String, remoteServerAlias:String){

	}
}