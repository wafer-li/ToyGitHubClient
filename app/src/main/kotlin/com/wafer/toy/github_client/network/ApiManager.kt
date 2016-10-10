package com.wafer.toy.github_client.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wafer.toy.github_client.BuildConfig
import com.wafer.toy.github_client.ToyGitHubClientApplication
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * The ApiManager class
 * Please put more info here.

 * @author wafer
 * *
 * @since 16/10/10 16:18
 */

object ApiManager {

    private const val BASE_URL: String = "https://api.github.com"
    private const val MAX_CACHE_SIZE: Long = 16 * 1024 * 1024

    private val client: OkHttpClient by lazy { createOkHttpClient() }
    private val retrofit: Retrofit by lazy { createRetrofit() }
    private val gson: Gson by lazy { createGson() }
    private val authenticator: Authenticator by lazy { createAuthenticator() }

    val services: ApiServices by lazy { createApiServices() }

    private fun createApiServices(): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

    private fun createRetrofit(): Retrofit {
        val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()

        retrofitBuilder
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)

        return retrofitBuilder.build()
    }

    private fun createGson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()

        initLog(builder)
        initCache(builder)
        initHeader(builder)

        return builder.build()
    }

    private fun initLog(builder: OkHttpClient.Builder) {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
    }

    private fun createAuthenticator(): Authenticator {
        TODO("check the oauth token to determine which authenticator should use")
    }

    private fun initHeader(builder: OkHttpClient.Builder) {
        builder.addInterceptor { it ->
            val originalRequest: Request = it.request()

            val request: Request = originalRequest.newBuilder()
                    .addHeader("Accept", "application/vnd.github.v3+json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authentication", authenticator.authToken())
                    .build()

            it.proceed(request)
        }
    }

    private fun initCache(builder: OkHttpClient.Builder) {
        val cacheDir: File = ToyGitHubClientApplication.appContext.cacheDir
        val cache: Cache = Cache(cacheDir, MAX_CACHE_SIZE)

        builder.cache(cache)
    }
}

