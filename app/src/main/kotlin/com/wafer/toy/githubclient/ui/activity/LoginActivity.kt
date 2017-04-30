package com.wafer.toy.githubclient.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.wafer.toy.githubclient.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_toolbar.setNavigationOnClickListener { onBackPressed() }

        login.setOnClickListener { Snackbar.make(it, "userName", Snackbar.LENGTH_LONG).show() }
    }
}
