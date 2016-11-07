package com.wafer.toy.github_client.ui.activity

import android.os.Bundle
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

        val modeBundle: Bundle = Bundle()

        if (oAuthToken == "") {
            modeBundle.putInt("mode", MAIN_EXPLORER)
        }
        else {
            modeBundle.putInt("mode", MAIN_DASHBOARD)
        }

        goToActivity(MainActivity::class.java, modeBundle)
    }
}
