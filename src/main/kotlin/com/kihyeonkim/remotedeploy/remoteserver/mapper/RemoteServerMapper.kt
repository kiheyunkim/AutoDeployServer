package com.kihyeonkim.remotedeploy.remoteserver.mapper

import com.kihyeonkim.remotedeploy.remoteserver.model.RemoteServerInfo
import org.apache.ibatis.annotations.Mapper

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-28
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Mapper
interface RemoteServerMapper {
	fun selectRemoteServerInfoList(): RemoteServerInfo

	fun selectRemoteServerConnectionInfo(remoteServerAlias: String): RemoteServerInfo?

	fun insertRemoteServerInfo(remoteServerInfo: RemoteServerInfo)

	fun deleteRemoteServerInfo(remoteServerAlias: String): Int

	fun selectRemoteServerAliasExist(remoteServerAlias: String): Boolean

	fun updateRemoteServerInfo(remoteServerInfo: RemoteServerInfo): Int
}