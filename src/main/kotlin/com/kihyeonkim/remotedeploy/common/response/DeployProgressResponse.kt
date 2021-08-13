package com.kihyeonkim.remotedeploy.common.response

import com.kihyeonkim.remotedeploy.jenkins.model.JenkinsLog

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-13
 * Github : http://github.com/kiheyunkim
 * Comment :
 */
data class DeployProgressResponse(var isAllEnd: Boolean, var buildLog: JenkinsLog){
}
