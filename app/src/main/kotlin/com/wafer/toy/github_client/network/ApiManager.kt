package com.wafer.toy.github_client.network

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wafer.toy.github_client.BuildConfig
import com.wafer.toy.github_client.utils.getOAuthKey
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * The ApiManager class
 *
 * Use for network manage
 * Init it first
 *
 * @author wafer
 * @since 16/10/10 16:18
 */

object ApiManager {
    private const val BASE_URL: String = "https://api.github.com/"
    private const val MAX_CACHE_SIZE: Long = 16 * 1024 * 1024

    private lateinit var context: Context
        private set

    lateinit var client: OkHttpClient
    private set

    lateinit var retrofit: Retrofit
    private set

    private val gson: Gson by lazy { createGson() }

    lateinit var services: ApiServices

    fun init(context: Context) {
        this.context = context.applicationContext

        client = createOkHttpClient(context)
        retrofit = createRetrofit()
        services = createApiServices()
    }

    fun changeClient(client: OkHttpClient) {
        this.client = client

        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        services = createApiServices()
    }

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

    private fun createOkHttpClient(context: Context): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()

        initLog(builder)
        initCache(builder, context)
        initHeader(builder)

        initAuthenticator(builder)

        return builder.build()
    }

    private fun initAuthenticator(builder: OkHttpClient.Builder) {
        val oAuthToken = getOAuthKey(context)

        if (oAuthToken != null) {
            builder.authenticator { route, response ->
                response.request().newBuilder()
                        .addHeader("Authorization", "token" + oAuthToken)
                        .build()
            }
        }
    }

    private fun initLog(builder: OkHttpClient.Builder) {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
    }

    private fun initHeader(builder: OkHttpClient.Builder) {
        builder.addInterceptor { it ->
            val originalRequest: Request = it.request()

            val request: Request = originalRequest.newBuilder()
                    .addHeader("Accept", "application/vnd.github.v3+json")
                    .addHeader("Content-Type", "application/json")
                    .build()

            it.proceed(request)
        }
    }

    private fun initCache(builder: OkHttpClient.Builder, context: Context) {
        val cacheDir: File = context.cacheDir
        val cache: Cache = Cache(cacheDir, MAX_CACHE_SIZE)

        builder.cache(cache)
    }
}

