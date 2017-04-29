package com.wafer.toy.githubclient.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.wafer.toy.githubclient.R
import com.wafer.toy.githubclient.application.Constants
import com.wafer.toy.githubclient.ui.adapter.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity() {

    enum class PageIndicator {
        MAIN, TRENDING
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
                .apply {
                    isDrawerSlideAnimationEnabled = false
                }

        drawer.addDrawerListener(toggle)
        toggle.syncState()

        nav_view_bottom.setNavigationItemSelectedListener { onNavigationItemSelected(it) }

        val pref = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)

        val isLogin = pref.getString(Constants.PREF_OAUTH_TOKEN, null).isNullOrEmpty().not()

        val pagerAdapter =
                if (isLogin)
                    MainPagerAdapter(supportFragmentManager, PageIndicator.MAIN, this)
                else
                    MainPagerAdapter(supportFragmentManager, PageIndicator.TRENDING, this)

        view_pager.adapter = pagerAdapter

        tabs.setupWithViewPager(view_pager)
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
