package com.wafer.toy.githubclient.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafer.toy.githubclient.R
import com.wafer.toy.githubclient.application.Constants
import java.text.SimpleDateFormat
import java.util.*


/**
 * The TrendingContentFragment class
 * Please put more info here.
 * @author wafer
 * @since 17/4/29 20:02
 */
class TrendingContentFragment : Fragment() {

    private lateinit var pageTitle: String

    private val trendingTitles = resources.getStringArray(R.array.trending_tab_titles)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageTitle = arguments.getString(Constants.PAGE_TITLE)
        val date = getPushedDate(trendingTitles.indexOf(pageTitle))

    }


    private fun getPushedDate(index: Int): String {
        // Monday as the first day of week
        val calendar = Calendar.getInstance().apply { firstDayOfWeek = Calendar.MONDAY }

        val dateLiteral: String = when (index) {
            0 -> {
                // Today
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
            }
            1 -> {
                // This week
                val date = calendar.apply { set(Calendar.DAY_OF_WEEK, firstDayOfWeek) }.time
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
            }
            2 -> {
                // This month
                val date = calendar.apply { set(Calendar.DAY_OF_MONTH, 1) }.time
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
            }
            else -> ""
        }

        return dateLiteral
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.content_main, container, false)

        return view
    }
}