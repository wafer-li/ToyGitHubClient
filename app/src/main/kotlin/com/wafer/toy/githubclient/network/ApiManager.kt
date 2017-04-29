package com.wafer.toy.githubclient.network

import android.content.Context

/**
 * The ApiManager class
 * Please put more info here.
 * @author wafer
 * @since 17/4/30 01:28
 */
object ApiManager {

    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context.applicationContext
    }


}