package com.kihyeonkim.remotedeploy.github.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kihyeonkim.remotedeploy.github.model.BranchInfo
import com.kihyeonkim.remotedeploy.github.model.RepositoryInfo
import com.kihyeonkim.remotedeploy.repo.model.GithubKeySet
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-16
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Component
class GithubApi(
	val sshKeyApi: SshKeyApi
) {
	private val objectMapper: ObjectMapper = ObjectMapper()
	private val repositoryListApi = "https://api.github.com/user/repos"
	private val repositoryBranchListApi: (String, String) -> String = { userName, repositoryName ->
		"https://api.github.com/repos/${userName}/${repositoryName}/branches"
	}
	private val deployKeyRegisterApi: (String, String) -> String = { userName, repositoryName ->
		"https://api.github.com/repos/${userName}/${repositoryName}/keys"
	}

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

	fun addDeployKeyAndSSHKey(githubKeySet: GithubKeySet, repoAlias: String, repositoryName: String): Boolean {
		val publicKey =
			sshKeyApi.saveRSAPrivateKeyAndGetPublicKey(repoAlias, repositoryName)

		val restTemplate = RestTemplate()
		val httpHeaders = HttpHeaders()
		httpHeaders.contentType = MediaType(MediaType.APPLICATION_JSON, Charsets.UTF_8)
		httpHeaders.add("Authorization", "token ${githubKeySet.accessToken}")

		val params = HashMap<String, String>()
		params["title"] = "${repositoryName.uppercase()}_DEPLOY"
		params["key"] = publicKey

		val body = objectMapper.writeValueAsString(params)

		val responseEntity = restTemplate.exchange(
			deployKeyRegisterApi(githubKeySet.userName, repositoryName),
			HttpMethod.POST,
			HttpEntity<String>(body, httpHeaders),
			String::class.java
		)

		return responseEntity.statusCode == HttpStatus.CREATED
	}
}