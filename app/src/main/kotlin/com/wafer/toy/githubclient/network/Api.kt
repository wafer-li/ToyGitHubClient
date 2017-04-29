package com.wafer.toy.githubclient.network

import com.wafer.toy.githubclient.model.network.Repo
import com.wafer.toy.githubclient.model.network.SearchResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

/**
 * The Api
 *
 * @author wafer
 * @since 17/4/30 01:35
 */
interface Api {

    @GET("/search/repositories")
    fun getTrendingRepos(@QueryMap(encoded = true) params: Map<String, String>): Flowable<SearchResponse<Repo>>

    fun getTrendingRepos(@Url url: String): Flowable<SearchResponse<Repo>>
}