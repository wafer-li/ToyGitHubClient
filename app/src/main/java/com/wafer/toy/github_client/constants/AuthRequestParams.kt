package com.wafer.toy.github_client.constants

import com.wafer.toy.github_client.model.AuthRequest

/**
 * The AuthRequest class
 * Please put more info here.
 * @author wafer
 * @since 16/10/26 18:40
 */


val SCOPE_LIST: List<String> = listOf(
        "user",
        "repo",
        "gist"
                                     )

const val NOTE: String = "GitHub Client written in Kotlin"

val AUTH_REQUEST: AuthRequest = AuthRequest(SCOPE_LIST, NOTE)
