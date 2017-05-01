package com.wafer.toy.githubclient.ui.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.wafer.toy.githubclient.R
import com.wafer.toy.githubclient.application.Constants
import com.wafer.toy.githubclient.ui.activity.MainActivity
import com.wafer.toy.githubclient.ui.fragment.MainContentFragment
import com.wafer.toy.githubclient.ui.fragment.TrendingContentFragment
import kotlin.properties.Delegates

/**
 * The MainPagerAdapter class
 * Please put more info here.
 * @author wafer
 * @since 17/4/29 19:39
 */

class MainPagerAdapter(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm) {

    var indicator by Delegates.observable(MainActivity.PageIndicator.TRENDING) { _, _, newValue ->
        when (newValue) {
            MainActivity.PageIndicator.MAIN -> {
                titles = context.resources.getStringArray(R.array.main_tab_titles)
                fragments = titles.map {
                    val args = Bundle().apply { putString(Constants.PAGE_TITLE, it) }
                    MainContentFragment().apply { arguments = args }
                }
            }

            MainActivity.PageIndicator.TRENDING -> {
                titles = context.resources.getStringArray(R.array.trending_tab_titles)
                fragments = titles.mapIndexed { index, title ->

                    val args = Bundle().apply { putString(Constants.PAGE_TITLE, title) }

                    TrendingContentFragment().apply {
                        arguments = args

                        if (index != 0)
                            isFragmentVisible = false
                    }
                }
            }
        }


    }

    private lateinit var titles: Array<out String>
    private lateinit var fragments: List<Fragment>

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = titles.size

    override fun getPageTitle(position: Int): CharSequence = titles[position]

}