package com.wafer.toy.github_client.ui.activity

import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.wafer.toy.github_client.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val drawer: Drawer by lazy { initDrawer() }
    private val accountHeader: AccountHeader by lazy { initAccountHeader() }


    override fun initView() {
        initToolbar()
        drawer
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    private fun initDrawer(): Drawer {
        return DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(accountHeader)
                .build()
    }

    private fun initAccountHeader(): AccountHeader {
        return AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.account_header_background)
                .build()
    }

}
