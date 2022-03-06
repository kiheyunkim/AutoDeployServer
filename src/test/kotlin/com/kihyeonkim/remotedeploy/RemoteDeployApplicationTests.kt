package com.kihyeonkim.remotedeploy

import com.jcraft.jsch.JSch
import com.jcraft.jsch.KeyPair
import com.kihyeonkim.remotedeploy.apis.github.api.GithubApi
import com.kihyeonkim.remotedeploy.deploy.mapper.DeployHistoryMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.ByteArrayOutputStream

@SpringBootTest
class RemoteDeployApplicationTests {


	@Autowired
	private lateinit var deployHistoryMapper: DeployHistoryMapper

	@Autowired
	private lateinit var githubApi: GithubApi

	fun saveRSAPrivateKeyAndGetPublicKey(repoAlias: String, repositoryName: String): String {
		val bytePrivateKey = ByteArrayOutputStream()
		val bytePublicKey = ByteArrayOutputStream()

		val generatedRSAKeySet = generateRSAKeySet()
		generatedRSAKeySet.writePrivateKey(bytePrivateKey)
		generatedRSAKeySet.writePublicKey(bytePublicKey, "")

		println(bytePrivateKey.toString())
		println(bytePublicKey.toString())

		return bytePublicKey.toString()
	}

	private fun generateRSAKeySet(): KeyPair {
		val jsch = JSch()

		return KeyPair.genKeyPair(jsch, KeyPair.RSA, 2048)
	}

	@Test
	fun contextLoads() {
		saveRSAPrivateKeyAndGetPublicKey("","")
	}
}
