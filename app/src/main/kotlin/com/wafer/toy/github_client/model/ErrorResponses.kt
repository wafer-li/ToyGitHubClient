package com.wafer.toy.github_client.model

/**
 * The Models module
 *
 * Containing the error response data class
 *
 * @author wafer
 * @since 16/10/14 01:50
 */

data class ValidationError(val resource: String? = null,
                           val field: String? = null,
                           val code: String? = null,
                           val message: String? = null)

data class ErrorResponse(val message: String? = null,
                         val documentUrl:String? = null)
