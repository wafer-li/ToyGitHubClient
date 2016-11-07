package com.wafer.toy.github_client.ui.activity

import android.os.Handler
import com.wafer.toy.github_client.R
import com.wafer.toy.github_client.constants.MAIN_DASHBOARD
import com.wafer.toy.github_client.constants.MAIN_EXPLORER
import com.wafer.toy.github_client.utils.getOAuthToken

class SplashActivity : BaseActivity() {
    override fun init() {
        Handler().postDelayed({ checkOAuthToken() }, 2000)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_splash
    }

    private fun checkOAuthToken() {
        val oAuthToken = getOAuthToken(this)

        if (oAuthToken == "") {
            goToActivity(MainActivity::class.java, flags = MAIN_EXPLORER)
        }
        else {
            goToActivity(MainActivity::class.java, flags = MAIN_DASHBOARD)
        }
    }
}
