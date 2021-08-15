package com.kihyeonkim.remotedeploy.docker.api

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.command.PullImageResultCallback
import com.github.dockerjava.api.model.*
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient
import com.github.dockerjava.transport.DockerHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.TimeUnit


/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-15
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Component
class DockerApi(
	@Value("\${jenkins.jenkinsHome}")
	private var jenkinsHome: String,

	@Value("\${jenkins.jenkinsPort}")
	private var jenkinsPort: String,

	@Value("\${docker.jenkinsContainerName}")
	private var jenkinsContainerName: String,

	@Value("\${docker.dockerHost}")
	private var dockerHost: String
) {
	private val dockerClientConfig: DockerClientConfig? = DefaultDockerClientConfig.createDefaultConfigBuilder()
		.withDockerHost(dockerHost)
		.build()

	private val httpClient: DockerHttpClient = ApacheDockerHttpClient.Builder()
		.dockerHost(dockerClientConfig!!.dockerHost)
		.maxConnections(100)
		.connectionTimeout(Duration.ofSeconds(30))
		.responseTimeout(Duration.ofSeconds(45))
		.build()

	private val dockerClient: DockerClient = DockerClientImpl.getInstance(dockerClientConfig, httpClient)

	fun pullJenkinsImage() {

		dockerClient.pullImageCmd("jenkins/jenkins")
			.withTag("alpine")
			.exec(PullImageResultCallback())
			.awaitCompletion(30, TimeUnit.SECONDS)
	}

	fun startJenkinsContainer() {
		val jenkinsFindList = dockerClient.listContainersCmd().withNameFilter(listOf(jenkinsContainerName)).exec()
		if (jenkinsFindList.size > 0) {
			return;
		}

		val hostConfig: HostConfig = HostConfig.newHostConfig().withPortBindings(
			listOf(
				PortBinding(Ports.Binding("127.0.0.1", jenkinsPort), ExposedPort(8080)),
				PortBinding(Ports.Binding("127.0.0.1", "50000"), ExposedPort(50000))
			)
		).withBinds(
			Binds(Bind(jenkinsHome, Volume("/var/jenkins_home")))
		)

		val startContainerCmd =
			dockerClient.createContainerCmd("jenkins/jenkins:alpine")
				.withName(jenkinsContainerName)
				.withHostConfig(hostConfig)
				.exec()

		dockerClient.startContainerCmd(startContainerCmd.id).exec()
	}
}