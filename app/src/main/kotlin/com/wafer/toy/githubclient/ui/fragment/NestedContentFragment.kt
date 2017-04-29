package com.wafer.toy.githubclient.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafer.toy.githubclient.R
import com.wafer.toy.githubclient.application.Constants

/**
 * The NestedContentFragment class
 * Please put more info here.
 * @author wafer
 * @since 17/4/29 20:02
 */
class NestedContentFragment : Fragment() {

    private lateinit var pageTitle: String

    private val mainTitles = resources.getStringArray(R.array.main_tab_titles)
    private val trendingTitles = resources.getStringArray(R.array.trending_tab_titles)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageTitle = arguments.getString(Constants.PAGE_TITLE)

        proceedMainTitle(mainTitles.indexOf(pageTitle))
        proceedMainTitle(trendingTitles.indexOf(pageTitle))
    }

    private fun proceedMainTitle(index: Int) {
        when (index) {
            0 -> {
                // Dashboard
            }
            1 -> {
                // Repos
            }

            else -> return
        }
    }

    private fun proceedTrendingTitle(index: Int) {
        when (index) {
            0 -> {
                // Today
            }

            1 -> {
                // This week
            }

            2 -> {
                // This month
            }

            else -> return
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.content_main, container, false)

        return view
    }
}