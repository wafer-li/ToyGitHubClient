package com.wafer.toy.githubclient.network.interceptors

import okhttp3.Interceptor

/**
 * The CacheRewriteResponseInterceptor
 * Please put more info here.
 * @author wafer
 * @since 17/5/1 17:18
 */
object CacheRewriteResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalResponse = chain.proceed(chain.request())
        val cacheControl = originalResponse.header("Cache-Control")

        if (isRemoteNoCache(cacheControl)) {
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + 5000)
                    .build()
        } else
            return originalResponse
    }

    private fun isRemoteNoCache(cacheControl: String?): Boolean =
            cacheControl == null ||
                    cacheControl.contains("no-store", true) ||
                    cacheControl.contains("no-cache", true) ||
                    cacheControl.contains("must-revalidate", true) ||
                    cacheControl.contains("max-age=0", true)

}