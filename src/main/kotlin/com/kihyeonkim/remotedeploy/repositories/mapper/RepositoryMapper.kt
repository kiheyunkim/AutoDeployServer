package com.kihyeonkim.remotedeploy.repositories.mapper

import com.kihyeonkim.remotedeploy.repositories.model.Repository
import org.apache.ibatis.annotations.Mapper

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-11
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Mapper
interface RepositoryMapper {
	fun selectRepositoryInfoList(pageCount: Int, offSet: Int): List<Repository>

	fun selectRepositoryDetail(repositoryAlias: String): Repository

	fun insertRepositoryInfo(repository: Repository): Int

	fun updateRepositoryInfo(repository: Repository): Int

	fun deleteRepositoryInfo(repositoryAlias: String): Int
}