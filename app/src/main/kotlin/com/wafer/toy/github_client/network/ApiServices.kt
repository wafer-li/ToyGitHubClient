package com.wafer.toy.github_client.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * The ApiServices class
 * Please put more info here.
 * @author wafer
 * @since 16/10/10 16:07
 */
interface ApiServices {

    /**
     * List the trending repo
     *
     * @param qualifiers `Map<String, String>` for GitHub repo search **"q"** qualifiers.
     * The first string MUST be **"q"**
     *
     *
     * @param defaultMap
     * The default map for sort and order, **DON'T CHANGE IT**
     */
    @GET("search/repositories")
    fun listTrendingRepo(@QueryMap qualifiers: Map<String, String>,
                         @QueryMap defaultMap: Map<String, String>
                         = hashMapOf(Pair("sort", "stars"), Pair("order", "desc")))
    : Call<Any>
}