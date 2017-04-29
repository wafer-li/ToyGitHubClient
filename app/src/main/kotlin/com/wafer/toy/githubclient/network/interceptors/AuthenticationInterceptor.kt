package com.wafer.toy.githubclient.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

/**
 * The AuthenticationInterceptor
 * Please put more info here.
 * @author wafer
 * @since 17/4/30 03:34
 */
class AuthenticationInterceptor(val authToken: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val originRequest = chain?.request()

        val builder = originRequest?.newBuilder()?.header("Authorization", authToken)

        return chain!!.proceed(builder?.build())
    }
}