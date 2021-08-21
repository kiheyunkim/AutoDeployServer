package com.kihyeonkim.remotedeploy.github.repository

import com.kihyeonkim.remotedeploy.github.model.SshConfigModel
import org.apache.ibatis.annotations.Mapper

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-21
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Mapper
interface SshConfigRepository {
	fun selectAllSshKeyInfo(): List<SshConfigModel>
	fun insertSshKeyInfo(sshConfigModel: SshConfigModel)
	fun deleteSshKeyInfo(hostname: String)
}