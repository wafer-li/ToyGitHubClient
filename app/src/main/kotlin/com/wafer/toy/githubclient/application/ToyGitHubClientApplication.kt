package com.wafer.toy.githubclient.application

import android.app.Application
import com.wafer.toy.githubclient.network.ApiManager

/**
 * The ToyGitHubClientApplication class
 * Please put more info here.
 * @author wafer
 * @since 17/4/30 01:31
 */
class ToyGitHubClientApplication : Application() {
    override fun onCreate() {
        ApiManager.init(this)
    }
}