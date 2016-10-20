package com.wafer.toy.github_client

import android.app.Application
import android.content.Context
import android.content.res.Resources

/**
 * The ToyGitHubClientApplication class
 * Please put more info here.
 * @author wafer
 * @since 16/10/10 18:02
 */
class ToyGitHubClientApplication : Application() {
    companion object {
        lateinit var appContext: Context
        private set

        lateinit var resource: Resources
        private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        resource = resources
    }
}