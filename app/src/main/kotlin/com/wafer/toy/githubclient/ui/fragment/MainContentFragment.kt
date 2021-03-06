package com.wafer.toy.githubclient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wafer.toy.githubclient.R

/**
 * The MainContentFragment
 * Please put more info here.
 * @author wafer
 * @since 17/4/30 15:03
 */
class MainContentFragment : Fragment() {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.content_main, container, false)
    }
}