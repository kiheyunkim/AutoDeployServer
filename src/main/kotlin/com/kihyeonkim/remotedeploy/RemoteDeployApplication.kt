package com.kihyeonkim.remotedeploy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RemoteDeployApplication

fun main(args: Array<String>) {
	runApplication<RemoteDeployApplication>(*args)
}
