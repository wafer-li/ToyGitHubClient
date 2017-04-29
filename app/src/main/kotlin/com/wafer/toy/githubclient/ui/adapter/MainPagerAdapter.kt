package com.wafer.toy.githubclient.ui.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.wafer.toy.githubclient.R
import com.wafer.toy.githubclient.application.Constants
import com.wafer.toy.githubclient.ui.activity.MainActivity
import com.wafer.toy.githubclient.ui.fragment.NestedContentFragment

/**
 * The MainPagerAdapter class
 * Please put more info here.
 * @author wafer
 * @since 17/4/29 19:39
 */

class MainPagerAdapter(fm: FragmentManager, indicator: MainActivity.PageIndicator, context: Context)
    : FragmentPagerAdapter(fm) {

    private val titles = when (indicator) {
        MainActivity.PageIndicator.MAIN ->
            context.resources.getStringArray(R.array.main_tab_titles)
        MainActivity.PageIndicator.TRENDING ->
            context.resources.getStringArray(R.array.trending_tab_titles)
    }!!

    private val fragments =
            titles.map {
                NestedContentFragment()
                        .apply {
                            val args = Bundle().apply { putString(Constants.PAGE_TITLE, it) }
                            arguments = args
                        }
            }


    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = titles.size

    override fun getPageTitle(position: Int): CharSequence = titles[position]

}