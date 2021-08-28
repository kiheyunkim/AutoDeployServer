package com.kihyeonkim.remotedeploy.deploy.mapper

import com.kihyeonkim.remotedeploy.deploy.model.RemoteServerConnectionInfo
import com.kihyeonkim.remotedeploy.deploy.model.RemoteServerModel
import org.apache.ibatis.annotations.Mapper

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-28
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Mapper
interface RemoteServerMapper {
	fun selectRemoteServerPublicKey(remoteServerAlias: String): List<RemoteServerConnectionInfo>?
	fun insertRemoteServerInfo(removeServerModel: RemoteServerModel)
	fun deleteRemoteServerInfo(remoteServerAlias: String): Int
	fun selectRemoteServerAliasExist(remoteServerAlias: String): Boolean
}