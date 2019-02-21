package com.wafer.toy.githubclient.network

import com.wafer.toy.githubclient.model.network.AuthRequest
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * The Api
 * Please put more info here.
 * @author wafer
 * @since 17/4/30 20:29
 */
interface Api {
    @POST("authorizations")
    fun getAuthCode(@Body authRequest: AuthRequest, @Header("X-GitHub-OTP") otpCode: String?)
            : Observable<ResponseBody>
}