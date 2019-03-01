package com.wafer.toy.githubclient.application

import android.app.Application
import com.wafer.toy.githubclient.network.ApiManager
import com.wafer.toy.githubclient.util.CredentialHelper

/**
 * The ToyGitHubClientApplication class
 * Please put more info here.
 * @author wafer
 * @since 17/4/30 01:31
 */
class ToyGitHubClientApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CredentialHelper.init(this)
        ApiManager.init(this)
    }
}