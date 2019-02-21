package com.wafer.toy.githubclient.application

/**
 * The Constants class
 * @author wafer
 * @since 17/4/29 16:36
 */

object Constants {

    const val APP_NAME = "ToyGitHubClient"

    const val PREF_NAME = "com.wafer.toy.githubclient.ToyGitHubClient"
    const val PREF_OAUTH_TOKEN = "PREF_OAUTH_TOKEN"

    const val PAGE_TITLE = "PAGE_TITLE"

    const val PREF_CACHE_SIZE = "PREF_CACHE_SIZE"

    const val SIZE_MB_IN_LONG = 1024 * 1024L

    const val DEFAULT_CACHE_SIZE = 16 * SIZE_MB_IN_LONG

    const val REQUEST_2FA_CODE = 0
    const val REQUEST_LOGIN = 1

    const val RESULT_SUCCESS = 1
    const val RESULT_FAIL = -1

    val DEFAULT_SCOPE = listOf("repo", "user", "gist")

}
