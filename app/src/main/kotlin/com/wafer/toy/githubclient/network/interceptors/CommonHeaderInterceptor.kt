package com.wafer.toy.githubclient.network.interceptors

import com.wafer.toy.githubclient.application.Constants
import okhttp3.Interceptor
import okhttp3.Response

/**
 * The CommonHeaderInterceptor
 * Please put more info here.
 * @author wafer
 * @since 17/4/30 04:19
 */
object CommonHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val originRequest = chain?.request()

        val builder = originRequest?.newBuilder()
                ?.header("Accept", "application/vnd.github.v3+json")
                ?.header("User-Agent", Constants.APP_NAME)

        return chain!!.proceed(builder?.build())
    }
}