package com.kihyeonkim.remotedeploy.github.api

import com.jcraft.jsch.JSch
import com.jcraft.jsch.KeyPair
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream


/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-20
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Component
class SshKeyApi(
	@Value("\${ssh.sshHome}")
	private var sshHome: String
) {
	fun saveRSAPrivateKeyAndGetPublicKey(keyName: String): String {
		val bytePrivateKey = ByteArrayOutputStream()
		val bytePublicKey = ByteArrayOutputStream()

		val generatedRSAKeySet = generateRSAKeySet()
		generatedRSAKeySet.writePrivateKey(bytePrivateKey)
		generatedRSAKeySet.writePublicKey(bytePublicKey, "")

		savePrivateKeyToDisk(keyName, bytePrivateKey)

		return bytePublicKey.toString()
	}

	private fun savePrivateKeyToDisk(keyName: String, bytePrivateKey: ByteArrayOutputStream) {
		FileOutputStream("$sshHome/$keyName").use { outputStream -> bytePrivateKey.writeTo(outputStream) }
	}

	private fun generateRSAKeySet(): KeyPair {
		val jsch = JSch()

		return KeyPair.genKeyPair(jsch, KeyPair.RSA, 2048)
	}
}