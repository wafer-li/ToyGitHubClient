package com.wafer.toy.github_client.model.responses

import com.google.gson.annotations.Expose
import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class OAuthTokenResponse(
        @field:SerializedName("app")
        @field:Expose
        val app: App? = null,

        @field:SerializedName("hashed_token")
        @field:Expose
        val hashedToken: String? = null,

        @field:SerializedName("note")
        @field:Expose
        val note: String? = null,

        @field:SerializedName("note_url")
        @field:Expose
        val noteUrl: String? = null,

        @field:SerializedName("updated_at")
        @field:Expose
        val updatedAt: String? = null,

        @field:SerializedName("token_last_eight")
        @field:Expose
        val tokenLastEight: String? = null,

        @field:SerializedName("fingerprint")
        @field:Expose
        val fingerprint: String? = null,

        @field:SerializedName("created_at")
        @field:Expose
        val createdAt: String? = null,

        @field:SerializedName("id")
        @field:Expose
        val id: Int? = null,

        @field:SerializedName("scopes")
        @field:Expose
        val scopes: List<String?>? = null,

        @field:SerializedName("url")
        @field:Expose
        val url: String? = null,

        @field:SerializedName("token")
        @field:Expose
        val token: String? = null)