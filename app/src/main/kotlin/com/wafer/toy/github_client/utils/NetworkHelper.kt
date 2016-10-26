package com.wafer.toy.github_client.utils

import android.content.Context
import android.content.SharedPreferences
import com.wafer.toy.github_client.constants.OAUTH_KEY
import com.wafer.toy.github_client.constants.SHARED_PREFERENCE_KEY
import com.wafer.toy.github_client.network.ApiManager
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * The NetworkHelper class
 * Please put more info here.
 * @author wafer
 * @since 16/10/25 21:28
 */

fun getOAuthKey(context: Context): String? {
    val preference: SharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)

    return preference.getString(OAUTH_KEY, null)
}


fun addBasicAuthencation(username: String, password: String): Retrofit {
    val client: OkHttpClient = ApiManager.client
    val builder: OkHttpClient.Builder = client.newBuilder()

    builder.authenticator { route, response ->
        val credential: String = Credentials.basic(username, password)
        response.request().newBuilder()
                .removeHeader("Authorization")
                .addHeader("Authorization", credential).build()
    }

    ApiManager.changeClient(client)
    return ApiManager.retrofit
}


fun addOAuthAuthenticator(oAuthToken: String): Retrofit {
    val client: OkHttpClient = ApiManager.client
    val builder: OkHttpClient.Builder = client.newBuilder()

    builder.authenticator { route, response ->
        response.request().newBuilder()
                .removeHeader("Authorization")
                .addHeader("Authorization", "token" + oAuthToken)
                .build()
    }

    ApiManager.changeClient(client)
    return ApiManager.retrofit
}

