package com.wafer.toy.githubclient.network

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * The ApiManager class
 * Please put more info here.
 * @author wafer
 * @since 17/4/30 01:28
 */
object ApiManager {

    const val BASE_URL = "https://api.github.com"

    private lateinit var context: Context

    private val gson: Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    private val retrofitBuilder: Retrofit.Builder =
            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))

    private val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

    fun init(context: Context) {
        this.context = context.applicationContext
    }

    fun createService(serviceClass: Class<Api>, username: String = "", password: String = ""): Api {
        if (username.isNotBlank() && password.isNotBlank()) {
            val basicToken = Credentials.basic(username, password)
            return createService(serviceClass, basicToken)
        }

        return createService(serviceClass)
    }

    fun createService(serviceClass: Class<Api>, authToken: String = ""): Api {
        if (authToken.isNotBlank()) {
            val interceptor = AuthenticationInterceptor(authToken)

            if (!clientBuilder.interceptors().contains(interceptor))
                clientBuilder.addInterceptor(interceptor)
        }

        val client = clientBuilder.build()
        val retrofit = retrofitBuilder.client(client).build()

        return retrofit.create(serviceClass)
    }
}