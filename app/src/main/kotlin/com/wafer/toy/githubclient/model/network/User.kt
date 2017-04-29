package com.wafer.toy.githubclient.model.network

import com.google.gson.annotations.SerializedName

data class User(

        @field:SerializedName("received_events_url")
        val receivedEventsUrl: String? = null,

        @field:SerializedName("avatar_url")
        val avatarUrl: String? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("login")
        val login: String? = null,

        @field:SerializedName("type")
        val type: String? = null,

        @field:SerializedName("gravatar_id")
        val gravatarId: String? = null,

        @field:SerializedName("url")
        val url: String? = null
)