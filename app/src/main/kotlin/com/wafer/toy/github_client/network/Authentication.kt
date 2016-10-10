package com.wafer.toy.github_client.network

/**
 * The Authentication class
 * Please put more info here.
 * @author wafer
 * @since 16/10/10 19:15
 */
interface Authentication {
    fun authToken(): String
}

class Authenticator(var authentication: Authentication) : Authentication by authentication {
    fun basic() {
        authentication = BasicAuthenticator
    }

    fun oAuth() {
        authentication = OAuthAuthenticator
    }
}

object BasicAuthenticator : Authentication {
    override fun authToken(): String {
        TODO("Get and save username & password first")
    }
}

object OAuthAuthenticator : Authentication {
    override fun authToken(): String {
        TODO("Use the basic auth first")
    }
}