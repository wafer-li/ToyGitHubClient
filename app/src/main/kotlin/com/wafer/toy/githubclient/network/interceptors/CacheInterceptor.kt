package com.wafer.toy.githubclient.network.interceptors

import com.wafer.toy.githubclient.network.ApiManager
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * The CacheInterceptor
 * Please put more info here.
 * @author wafer
 * @since 17/4/30 04:35
 */
class CacheInterceptor(val cacheControl: CacheControl) : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain!!.request()

        if (!ApiManager.isNetworkAvailable()) {
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
        }

        return chain.proceed(request)
    }
}