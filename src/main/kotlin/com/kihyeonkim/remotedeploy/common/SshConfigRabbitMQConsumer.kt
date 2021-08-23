package com.kihyeonkim.remotedeploy.common

import com.kihyeonkim.remotedeploy.common.enumeration.SshConfigMessageType
import com.kihyeonkim.remotedeploy.common.model.SshConfigMessageModel
import com.kihyeonkim.remotedeploy.github.model.SshConfigModel
import com.kihyeonkim.remotedeploy.github.repository.SshConfigRepository
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.SerializationUtils
import java.io.File
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.attribute.PosixFilePermissions

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-21
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Component
class SshConfigRabbitMQConsumer(
	private val sshConfigRepository: SshConfigRepository
) {
	@Value("\${ssh.sshHome}")
	private lateinit var sshHome: String

	private val configTemplate: (String, String, String) -> String = { host, hostName, identityFileName ->
		"""Host $host
	HostName $hostName
	StrictHostKeyChecking no
	IdentityFile /var/jenkins_home/.ssh/$identityFileName

""".trimIndent()
	}

	@RabbitListener(queues = ["sshConfig"])
	fun sshConfigMessage(message: Message) {
		val deserializedObject: SshConfigMessageModel =
			SerializationUtils.deserialize(message.body) as SshConfigMessageModel

		when (deserializedObject.sshConfigMessageType) {
			SshConfigMessageType.ADD -> sshConfigRepository.insertSshKeyInfo(
				SshConfigModel(
					deserializedObject.host,
					deserializedObject.hostName,
					deserializedObject.identityFileName
				)
			)
			SshConfigMessageType.DELETE -> sshConfigRepository.deleteSshKeyInfo(deserializedObject.host)
		}

		val sshKeyInfos = sshConfigRepository.selectAllSshKeyInfo()
		val configStringBuilder = StringBuilder()
		for (sshKeyInfo in sshKeyInfos) {
			configStringBuilder.append(
				configTemplate(
					sshKeyInfo.host,
					sshKeyInfo.hostName,
					sshKeyInfo.identityFileName
				)
			)
		}

		FileWriter("$sshHome/config").use { it.write(configStringBuilder.toString()) }

		val file = File("$sshHome/config")
		Files.setPosixFilePermissions(file.toPath(), PosixFilePermissions.fromString("rw-------"))
	}
}