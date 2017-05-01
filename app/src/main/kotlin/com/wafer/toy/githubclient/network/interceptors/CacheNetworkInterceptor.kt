package com.wafer.toy.githubclient.network.interceptors

import okhttp3.Interceptor

/**
 * The CacheNetworkInterceptor
 * Please put more info here.
 * @author wafer
 * @since 17/5/1 17:18
 */
class CacheNetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalResponse = chain.proceed(chain.request())
        val cacheControl = originalResponse.header("Cache-Control")
        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + 5000)
                    .build()
        } else {
            return originalResponse
        }
    }
}