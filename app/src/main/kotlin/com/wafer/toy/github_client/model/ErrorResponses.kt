package com.wafer.toy.github_client.model

/**
 * The Models module
 *
 * Containing the error response data class
 *
 * @author wafer
 * @since 16/10/14 01:50
 */

data class ValidationError(val resource: String?, val field: String?, val code: String?, val message: String?)

data class ErrorResponse(val message: String, val documentUrl:String?)
