package com.kihyeonkim.remotedeploy.deploy.mapper

import com.kihyeonkim.remotedeploy.deploy.enumeration.DeployState
import com.kihyeonkim.remotedeploy.deploy.model.DeployHistory
import org.apache.ibatis.annotations.Mapper

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-13
 * Github : http://github.com/kiheyunkim
 * Comment :
 */
@Mapper
interface DeployHistoryMapper {
	fun selectDeployHistory(page: Int, offset: Int)
	fun insertDeployHistory(deployHistory: DeployHistory)
	fun updateDeployHistory(id: Int, deployState: DeployState)
}