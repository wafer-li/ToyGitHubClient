package com.wafer.toy.githubclient.model.network

import com.google.gson.annotations.SerializedName

data class AuthRequest(

        @field:SerializedName("note")
        val note: String? = null,

        @field:SerializedName("client_id")
        val clientId: String? = null,

        @field:SerializedName("client_secret")
        val clientSecret: String? = null,

        @field:SerializedName("scopes")
        val scopes: List<String?>? = null
)