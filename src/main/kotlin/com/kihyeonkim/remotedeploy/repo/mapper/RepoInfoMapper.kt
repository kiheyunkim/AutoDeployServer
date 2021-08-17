package com.kihyeonkim.remotedeploy.repo.mapper

import com.kihyeonkim.remotedeploy.repo.model.GithubKeySet
import com.kihyeonkim.remotedeploy.repo.model.RepoInfo
import org.apache.ibatis.annotations.Mapper

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-16
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Mapper
interface RepoInfoMapper {
	fun selectRepoAliasList(): List<String>
	fun selectRepoInfo(repoAlias: String): GithubKeySet
	fun insertRepoInfo(repoInfo: RepoInfo)
	fun updateRepoInfo(repoInfo: RepoInfo)
	fun deleteRepoInfo(repoAlias: String)
}