package com.wafer.toy.github_client.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * The Models module
 *
 * Containing the error response data class
 *
 * @author wafer
 * @since 16/10/14 01:50
 */

data class ValidationError(
        @field:SerializedName("resources")
        @Expose
        val resource: String? = null,

        @field:SerializedName("field")
        @Expose
        val field: String? = null,

        @field:SerializedName("code")
        @Expose
        val code: String? = null,

        @field:SerializedName("message")
        @Expose
        val message: String? = null)

data class ErrorResponse(val message: String? = null,
                         val documentUrl:String? = null)
