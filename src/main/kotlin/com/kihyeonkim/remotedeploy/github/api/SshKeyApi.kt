package com.kihyeonkim.remotedeploy.github.api

import com.jcraft.jsch.JSch
import com.jcraft.jsch.KeyPair
import com.kihyeonkim.remotedeploy.common.enumeration.SshConfigMessageType
import com.kihyeonkim.remotedeploy.common.model.SshConfigMessageModel
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.attribute.PosixFilePermissions


/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-20
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Component
class SshKeyApi(
	@Value("\${ssh.sshHome}")
	private var sshHome: String,
	private var rabbitTemplate: RabbitTemplate
) {
	fun saveRSAPrivateKeyAndGetPublicKey(repoAlias: String, repositoryName: String): String {
		val bytePrivateKey = ByteArrayOutputStream()
		val bytePublicKey = ByteArrayOutputStream()

		val generatedRSAKeySet = generateRSAKeySet()
		generatedRSAKeySet.writePrivateKey(bytePrivateKey)
		generatedRSAKeySet.writePublicKey(bytePublicKey, "")

		savePrivateKeyToDisk(repoAlias, keyName = repositoryName, bytePrivateKey)

		rabbitTemplate.convertAndSend(
			"sshConfig",
			SshConfigMessageModel(
				SshConfigMessageType.ADD,
				"${repoAlias.uppercase()}_${repositoryName.uppercase()}",
				"github.com",
				"${repoAlias.uppercase()}_${repositoryName.uppercase()}"
			)
		)

		return bytePublicKey.toString()
	}

	fun removeRSAPrivateKey(repoAlias: String, repositoryName: String): Boolean {

		deletePrivateKeyFromDisk(repoAlias, keyName = repositoryName)

		rabbitTemplate.convertAndSend(
			"sshConfig",
			SshConfigMessageModel(
				SshConfigMessageType.DELETE,
				"${repoAlias.uppercase()}_${repositoryName.uppercase()}",
				"",
				""
			)
		)

		return true
	}

	private fun deletePrivateKeyFromDisk(repoAlias: String, keyName: String) {
		val file = File("$sshHome/${repoAlias.uppercase()}_${keyName.uppercase()}")
		file.delete()
	}

	private fun savePrivateKeyToDisk(repoAlias: String, keyName: String, bytePrivateKey: ByteArrayOutputStream) {
		FileOutputStream("$sshHome/${repoAlias.uppercase()}_${keyName.uppercase()}").use { outputStream ->
			bytePrivateKey.writeTo(
				outputStream
			)
		}
		val file = File("$sshHome/${repoAlias.uppercase()}_${keyName.uppercase()}")
		Files.setPosixFilePermissions(file.toPath(), PosixFilePermissions.fromString("rw-------"))
	}

	private fun generateRSAKeySet(): KeyPair {
		val jsch = JSch()

		return KeyPair.genKeyPair(jsch, KeyPair.RSA, 2048)
	}
}