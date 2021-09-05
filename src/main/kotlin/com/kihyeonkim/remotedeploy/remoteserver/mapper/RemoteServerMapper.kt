package com.kihyeonkim.remotedeploy.remoteserver.mapper

import com.kihyeonkim.remotedeploy.remoteserver.mdoel.RemoteServerConnectionInfo
import com.kihyeonkim.remotedeploy.remoteserver.mdoel.RemoteServerInfoModel
import com.kihyeonkim.remotedeploy.remoteserver.mdoel.RemoteServerModel
import org.apache.ibatis.annotations.Mapper

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-28
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Mapper
interface RemoteServerMapper {
	fun selectRemoteServerInfoList(): RemoteServerInfoModel
	fun selectRemoteServerConnectionInfo(remoteServerAlias: String): RemoteServerConnectionInfo?
	fun insertRemoteServerInfo(remoteServerModel: RemoteServerModel)
	fun deleteRemoteServerInfo(remoteServerAlias: String): Int
	fun selectRemoteServerAliasExist(remoteServerAlias: String): Boolean
}