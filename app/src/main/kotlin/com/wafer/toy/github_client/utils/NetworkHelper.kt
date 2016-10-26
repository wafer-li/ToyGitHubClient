package com.wafer.toy.github_client.utils

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.support.v4.net.ConnectivityManagerCompat
import com.wafer.toy.github_client.constants.AUTH_REQUEST
import com.wafer.toy.github_client.constants.OAUTH_KEY
import com.wafer.toy.github_client.constants.SHARED_PREFERENCE_KEY
import com.wafer.toy.github_client.model.responses.OAuthTokenResponse
import com.wafer.toy.github_client.network.ApiManager
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

/**
 * The NetworkHelper
 *
 * Just some util method when interacting with network
 *
 * @author wafer
 * @since 16/10/25 21:28
 */

fun isOnline(context: Context): Boolean {

    val appContext = context.applicationContext

    val connectivityManager: ConnectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return connectivityManager.activeNetworkInfo.isConnected
}


fun getOAuthKey(context: Context): String? {
    val preference: SharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)

    return preference.getString(OAUTH_KEY, null)
}


fun addBasicAuthentication(username: String, password: String): Retrofit {
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

fun login(username: String, password: String,
          twoFactorAuthorizationCode: String? = null,
          callback: (response: Response<OAuthTokenResponse>?,
                     t: Throwable?) -> Unit) {

    addBasicAuthentication(username, password)
    ApiManager.services.login(username, password, twoFactorAuthorizationCode, AUTH_REQUEST)
            .enqueue(object : Callback<OAuthTokenResponse> {
                override fun onResponse(call: Call<OAuthTokenResponse>?,
                                        response: Response<OAuthTokenResponse>?) {
                    callback(response, null)
                }

                override fun onFailure(call: Call<OAuthTokenResponse>?, t: Throwable?) {
                    callback(null, t)
                }
            })
}
