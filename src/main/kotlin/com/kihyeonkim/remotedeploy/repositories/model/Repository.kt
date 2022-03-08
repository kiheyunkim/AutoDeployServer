package com.kihyeonkim.remotedeploy.repositories.model

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-11
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Repository(
	var repositoryAlias: String?,
	var scmAlias: String?,
	var repositorySshUrl: String?,
	var isPrivate: Boolean = false
)
