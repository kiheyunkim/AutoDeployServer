package com.kihyeonkim.remotedeploy.apis.github.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kihyeonkim.remotedeploy.apis.github.model.BranchInfo
import com.kihyeonkim.remotedeploy.apis.github.model.RepositoryInfo
import com.kihyeonkim.remotedeploy.deploy.model.GithubKeySetModel
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
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
		"https://api.github.com/repos/$userName/$repositoryName/branches"
	}
	private val deployKeyRegisterApi: (String, String) -> String = { userName, repositoryName ->
		"https://api.github.com/repos/$userName/$repositoryName/keys"
	}
	private val repositoryInfoApi: (String, String) -> String = { userName, repositoryName ->
		"https://api.github.com/repos/$userName/$repositoryName"
	}

	fun getRepositoryList(accessToken: String): List<RepositoryInfo> {
		var currentPage = 1

		val repositoryList = mutableListOf<RepositoryInfo>()

		while (true) {
			val restTemplate = RestTemplate()
			val httpHeaders = HttpHeaders()
			httpHeaders.contentType = MediaType(MediaType.APPLICATION_JSON, Charsets.UTF_8)
			httpHeaders.add("Authorization", "token $accessToken")

			val uriComponents: UriComponents = UriComponentsBuilder.fromHttpUrl(repositoryListApi)
				.queryParam("per_page", 100)
				.queryParam("page", currentPage)
				.build(false)

			val responseEntity = restTemplate.exchange(
				uriComponents.toUriString(),
				HttpMethod.GET,
				HttpEntity<String>(httpHeaders),
				String::class.java
			)

			val itemType = object : TypeToken<ArrayList<RepositoryInfo>>() {}.type

			val response = Gson().fromJson<ArrayList<RepositoryInfo>>(responseEntity.body, itemType)
			if (response.size == 0) {
				break;
			}

			repositoryList.addAll(Gson().fromJson(responseEntity.body, itemType))
			++currentPage
		}

		return repositoryList
	}

	fun getRepositoryBranchList(githubKeySetModel: GithubKeySetModel, repositoryName: String) {
		val restTemplate = RestTemplate()
		val httpHeaders = HttpHeaders()
		httpHeaders.contentType = MediaType(MediaType.APPLICATION_JSON, Charsets.UTF_8)
		httpHeaders.add("Authorization", "token ${githubKeySetModel.accessToken}")

		val uriComponents: UriComponents =
			UriComponentsBuilder.fromHttpUrl(repositoryBranchListApi(githubKeySetModel.userName, repositoryName))
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

	fun addDeployKeyAndSSHKey(
		githubKeySetModel: GithubKeySetModel,
		repoAlias: String,
		repositoryName: String
	): Boolean {
		val publicKey =
			sshKeyApi.saveRSAPrivateKeyAndGetPublicKey(repoAlias, repositoryName)

		val restTemplate = RestTemplate()
		val httpHeaders = HttpHeaders()
		httpHeaders.contentType = MediaType(MediaType.APPLICATION_JSON, Charsets.UTF_8)
		httpHeaders.add("Authorization", "token ${githubKeySetModel.accessToken}")

		val params = HashMap<String, String>()
		params["title"] = "${repositoryName.uppercase()}_DEPLOY"
		params["key"] = publicKey

		val body = objectMapper.writeValueAsString(params)

		val responseEntity = restTemplate.exchange(
			deployKeyRegisterApi(githubKeySetModel.userName, repositoryName),
			HttpMethod.POST,
			HttpEntity<String>(body, httpHeaders),
			String::class.java
		)

		return responseEntity.statusCode == HttpStatus.CREATED
	}

	fun checkApiKeyValidation(accessToken: String): Boolean {
		val restTemplate = RestTemplate()
		val httpHeaders = HttpHeaders()
		httpHeaders.contentType = MediaType(MediaType.APPLICATION_JSON, Charsets.UTF_8)
		httpHeaders.add("Authorization", "token $accessToken")

		val uriComponents: UriComponents = UriComponentsBuilder.fromHttpUrl(repositoryListApi)
			.build(false)

		val responseEntity = try {
			restTemplate.exchange(
				uriComponents.toUriString(),
				HttpMethod.GET,
				HttpEntity<String>(httpHeaders),
				String::class.java
			)
		} catch (restClientException: RestClientException) {
			return false;
		}


		return responseEntity.statusCode == HttpStatus.OK
	}

	fun getRepositorySshUrl(username: String, repositoryName: String, accessToken: String): String {
		val restTemplate = RestTemplate()
		val httpHeaders = HttpHeaders()
		httpHeaders.contentType = MediaType(MediaType.APPLICATION_JSON, Charsets.UTF_8)
		httpHeaders.add("Authorization", "token $accessToken")

		val uriComponents: UriComponents = UriComponentsBuilder.fromHttpUrl(repositoryInfoApi(username, repositoryName))
			.build(false)

		val responseEntity = try {
			restTemplate.exchange(
				uriComponents.toUriString(),
				HttpMethod.GET,
				HttpEntity<String>(httpHeaders),
				String::class.java
			)
		} catch (restClientException: RestClientException) {
			throw restClientException
		}

		val itemType = object : TypeToken<RepositoryInfo>() {}.type
		val parsedResult = Gson().fromJson<RepositoryInfo>(responseEntity.body, itemType)

		return parsedResult.sshUrl
	}

	//ToDo: deploy Key 삭제 로직 가능하면 추가.
	fun removeSSHKey(repoAlias: String, repositoryName: String): Boolean {
		return sshKeyApi.removeRSAPrivateKey(repoAlias, repositoryName)
	}
}