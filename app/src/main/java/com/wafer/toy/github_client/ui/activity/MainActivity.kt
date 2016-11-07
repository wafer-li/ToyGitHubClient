package com.wafer.toy.github_client.ui.activity

import com.wafer.toy.github_client.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun initView() {
        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    override fun getLayoutRes(): Int{
        return R.layout.activity_main
    }
}