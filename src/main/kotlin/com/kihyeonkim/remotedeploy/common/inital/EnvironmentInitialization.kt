package com.kihyeonkim.remotedeploy.common.inital

import com.kihyeonkim.remotedeploy.docker.api.DockerApi
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-16
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Component
class EnvironmentInitialization(private val dockerApi: DockerApi) {

	@PostConstruct
	fun createJenkinsContainer() {
		dockerApi.pullJenkinsImage()
		dockerApi.startJenkinsContainer()
	}
}