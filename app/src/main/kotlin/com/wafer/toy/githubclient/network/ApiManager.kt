package com.wafer.toy.githubclient.network

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wafer.toy.githubclient.application.Constants
import com.wafer.toy.githubclient.network.interceptors.AuthenticationInterceptor
import com.wafer.toy.githubclient.network.interceptors.CacheInterceptor
import com.wafer.toy.githubclient.network.interceptors.CommonHeaderInterceptor
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates


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

    private lateinit var pref: SharedPreferences

    var cacheSize: Long by Delegates.observable(Constants.DEFAULT_CACHE_SIZE) { _, _, newValue ->
        cache = Cache(context.cacheDir, newValue)
        clientBuilder.cache(cache)
    }

    private lateinit var cache: Cache

    private val cacheControl =
            CacheControl.Builder()
                    .maxAge(0, TimeUnit.SECONDS)
                    .maxStale(365, TimeUnit.DAYS)
                    .build()

    private val retrofitBuilder: Retrofit.Builder =
            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))

    private val clientBuilder: OkHttpClient.Builder =
            OkHttpClient.Builder()
                    .addInterceptor(CommonHeaderInterceptor())
                    .addInterceptor(CacheInterceptor(cacheControl))

    fun init(context: Context) {
        this.context = context.applicationContext
        pref = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        cacheSize = pref.getInt(Constants.PREF_CACHE_SIZE, 16) * Constants.SIZE_MB_IN_LONG
    }

    fun isNetworkAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting

        return isConnected
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

