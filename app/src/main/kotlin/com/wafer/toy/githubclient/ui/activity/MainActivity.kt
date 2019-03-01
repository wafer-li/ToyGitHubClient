package com.wafer.toy.githubclient.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.wafer.toy.githubclient.R
import com.wafer.toy.githubclient.application.Constants
import com.wafer.toy.githubclient.model.network.User
import com.wafer.toy.githubclient.network.Api
import com.wafer.toy.githubclient.network.ApiManager
import com.wafer.toy.githubclient.ui.adapter.MainPagerAdapter
import com.wafer.toy.githubclient.ui.fragment.TrendingContentFragment
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {

    enum class PageIndicator {
        MAIN, TRENDING
    }

    private var indicator = PageIndicator.TRENDING
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

        if (ApiManager.isLogin()) {
            updateDrawerHeader()
        } else {
            nav_view.getHeaderView(0).imageView.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivityForResult(intent, Constants.REQUEST_LOGIN)
                drawer.closeDrawer(GravityCompat.START)
            }
        }

        indicator = if (ApiManager.isLogin()) PageIndicator.MAIN else PageIndicator.TRENDING

        pagerAdapter = MainPagerAdapter(supportFragmentManager, applicationContext)

        pagerAdapter.indicator = indicator  //Init the indicator
        updatePageState(indicator)

        view_pager.adapter = pagerAdapter

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
            ApiManager.api = ApiManager.createService(Api::class.java, "token ${ApiManager.token}")
            updatePageState(PageIndicator.MAIN)
            updateDrawerHeader()
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

    private fun updateDrawerHeader() {
        ApiManager.api.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<User> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: User) {
                        val headerView = nav_view.getHeaderView(0)

                        Glide.with(this@MainActivity)
                                .load(t.avatarUrl)
                                .into(headerView.imageView)

                        headerView.navUsername.text = t.userName
                    }

                    override fun onError(e: Throwable) {
                        when (e) {
                            is HttpException -> {
                                ApiManager.token

                                Log.d("Nav Update Error", e.message() + "\n" +
                                        e.response().errorBody()?.string())
                            }
                        }
                    }
                })

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
