package com.kihyeonkim.remotedeploy.github.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jcraft.jsch.JSch
import com.jcraft.jsch.KeyPair
import com.kihyeonkim.remotedeploy.github.model.BranchInfo
import com.kihyeonkim.remotedeploy.github.model.RepositoryInfo
import com.kihyeonkim.remotedeploy.repo.model.GithubKeySet
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder
import java.io.ByteArrayOutputStream
import java.security.SecureRandom

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-16
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Component
class GithubApi {
	private val repositoryListApi = "https://api.github.com/user/repos"
	private val repositoryBranchListApi: (String, String) -> String = { userName, repositoryName ->
		"https://api.github.com/repos/${userName}/${repositoryName}/branches"
	}
	private val secureRandom: SecureRandom = SecureRandom()

	fun getRepositoryList(accessToken: String): ArrayList<RepositoryInfo> {
		val restTemplate = RestTemplate()
		val httpHeaders = HttpHeaders()
		httpHeaders.contentType = MediaType(MediaType.APPLICATION_JSON, Charsets.UTF_8)

		val uriComponents: UriComponents = UriComponentsBuilder.fromHttpUrl(repositoryListApi)
			.queryParam("access_token", accessToken)
			.queryParam("per_page", 100)
			.build(false)

		val responseEntity = restTemplate.exchange(
			uriComponents.toUriString(),
			HttpMethod.GET,
			HttpEntity<String>(httpHeaders),
			String::class.java
		)

		val itemType = object : TypeToken<ArrayList<RepositoryInfo>>() {}.type

		return Gson().fromJson(responseEntity.body, itemType)
	}

	fun getRepositoryBranchList(githubKeySet: GithubKeySet, repositoryName: String) {
		val restTemplate = RestTemplate()
		val httpHeaders = HttpHeaders()
		httpHeaders.contentType = MediaType(MediaType.APPLICATION_JSON, Charsets.UTF_8)
		httpHeaders.add("Authorization", "token ${githubKeySet.accessToken}")

		val uriComponents: UriComponents =
			UriComponentsBuilder.fromHttpUrl(repositoryBranchListApi(githubKeySet.userName, repositoryName))
				.build(false)

		val responseEntity = restTemplate.exchange(
			uriComponents.toUriString(),
			HttpMethod.GET,
			HttpEntity<String>(httpHeaders),
			String::class.java
		)

		val itemType = object : TypeToken<ArrayList<BranchInfo>>() {}.type

		return Gson().fromJson(responseEntity.body, itemType)
	}

	fun addDeployKeyAndGetPrivateKey(githubKeySet: GithubKeySet, repositoryName: String): String {
		val restTemplate = RestTemplate()
		val httpHeaders = HttpHeaders()
		httpHeaders.contentType = MediaType(MediaType.APPLICATION_JSON, Charsets.UTF_8)
		httpHeaders.add("Authorization", "token ${githubKeySet.accessToken}")

		val generatedRSAKeySet = generateRSAKeySet()




		return ""
	}

	fun generateRSAKeySet(): Pair<String, String> {
		val bytePrivateKey = ByteArrayOutputStream()
		val bytePublicKey = ByteArrayOutputStream()

		val keyPair: KeyPair = KeyPair.genKeyPair(JSch(), KeyPair.RSA, 2048)
		keyPair.writePrivateKey(bytePrivateKey, "".toByteArray())
		keyPair.writePublicKey(bytePublicKey, "")

		println(bytePrivateKey.toString())
		println(bytePublicKey.toString())

		return Pair(bytePrivateKey.toString(), bytePublicKey.toString())
	}
}