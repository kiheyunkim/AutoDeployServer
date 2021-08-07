package com.kihyeonkim.remotedeploy.deploy.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-07
 * Github : http://github.com/kiheyunkim
 * Comment :
 */
@Controller
@RequestMapping("/deploy")
class DeployController {
	@GetMapping
	fun getTest(){

	}

}