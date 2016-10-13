package com.wafer.toy.github_client

/**
 * The Models module
 *
 * Containing the data class of the whole application
 *
 * @author wafer
 * @since 16/10/14 01:50
 */

data class ValidationError(val resource: String?, val field: String?, val code: String?, val message: String?)
