package com.wafer.toy.github_client.model

/**
 * The Requests module
 *
 * Containing the network request data class
 *
 * @author wafer
 * @since 16/10/14 03:05
 */

data class AuthRequst(val scopes: List<String>, val note: String)
