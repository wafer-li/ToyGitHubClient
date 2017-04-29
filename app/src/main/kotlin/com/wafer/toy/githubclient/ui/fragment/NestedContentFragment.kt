package com.wafer.toy.githubclient.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafer.toy.githubclient.R

/**
 * The NestedContentFragment class
 * Please put more info here.
 * @author wafer
 * @since 17/4/29 20:02
 */
class NestedContentFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.content_main, container, false)

        return view
    }
}