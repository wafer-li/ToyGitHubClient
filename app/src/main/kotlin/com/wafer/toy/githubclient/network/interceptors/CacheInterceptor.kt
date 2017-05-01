package com.wafer.toy.githubclient.network.interceptors

import com.wafer.toy.githubclient.network.ApiManager
import okhttp3.CacheControl
import okhttp3.Interceptor


/**
 * The CacheInterceptor
 * Please put more info here.
 * @author wafer
 * @since 17/4/30 04:35
 */
class CacheInterceptor(val cacheControl: CacheControl) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        if (!ApiManager.isNetworkAvailable()) {
            request = request.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached")
                    .build()
        }
        return chain.proceed(request)
    }
}
