package com.wafer.toy.githubclient.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.wafer.toy.githubclient.R
import com.wafer.toy.githubclient.application.Constants
import com.wafer.toy.githubclient.ui.adapter.MainPagerAdapter
import com.wafer.toy.githubclient.ui.fragment.TrendingContentFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity() {

    enum class PageIndicator {
        MAIN, TRENDING
    }

    var indicator = PageIndicator.TRENDING
    lateinit var pagerAdapter: MainPagerAdapter

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
        nav_view.getHeaderView(0).imageView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, Constants.REQUEST_LOGIN)
        }

        val pref = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)

        val isLogin = pref.getString(Constants.PREF_OAUTH_TOKEN, null).isNullOrEmpty().not()

        indicator = if (isLogin) PageIndicator.MAIN else PageIndicator.TRENDING

        pagerAdapter = MainPagerAdapter(supportFragmentManager, applicationContext)

        pagerAdapter.indicator = indicator  //Init the indicator
        updatePageState(indicator)

        view_pager.adapter = pagerAdapter

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                val fragment = pagerAdapter.getItem(position)

                if (fragment is TrendingContentFragment)
                    fragment.isFragmentVisible = true
            }

        })

        tabs.setupWithViewPager(view_pager)
    }

    private fun updatePageState(indicator: PageIndicator) {

        if (pagerAdapter.indicator != indicator) {
            pagerAdapter.indicator = indicator
            pagerAdapter.notifyDataSetChanged()
        }

        when (indicator) {
            PageIndicator.MAIN -> {
                fab.visibility = View.VISIBLE
                toolbar.title = getString(R.string.app_name)
            }
            PageIndicator.TRENDING -> {
                fab.visibility = View.GONE
                toolbar.title = getString(R.string.title_trending)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("Main Activity Result", "Return!")
        if (requestCode == Constants.REQUEST_LOGIN && resultCode == Constants.RESULT_SUCCESS) {
            updatePageState(PageIndicator.MAIN)
            Log.d("Main Activity Result", "Return From Login")
        }

        super.onActivityResult(requestCode, resultCode, data)
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

        when (id) {
            R.id.nav_exit -> finish()
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
