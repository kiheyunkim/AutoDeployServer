package com.kihyeonkim.remotedeploy.common.response

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-08-12
 * Github : http://github.com/kiheyunkim
 * Comment :
 */
data class DeployResponse<T>(var result: T, var errorCode: String?, var errorMsg: String?) {
	constructor(result: T) : this(result, null, null)
}