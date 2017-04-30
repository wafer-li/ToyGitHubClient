package com.wafer.toy.githubclient.network

import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * The Api
 *
 * @author wafer
 * @since 17/4/30 01:35
 */
interface Trending {

    @GET(".")
    fun getTrending(@QueryMap(encoded = true) params: Map<String, String> = mapOf()): Flowable<Response<ResponseBody>>
}