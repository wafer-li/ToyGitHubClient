package com.wafer.toy.githubclient.network

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The Api
 *
 * @author wafer
 * @since 17/4/30 01:35
 */
interface TrendingApi {

    @GET("{language}")
    fun getTrending(@Path("language") language: String = ".", @Query("since") since: String)
            : Observable<ResponseBody>
}