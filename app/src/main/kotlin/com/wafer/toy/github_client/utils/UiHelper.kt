package com.wafer.toy.github_client.utils

import android.app.Activity
import android.util.DisplayMetrics
import android.view.Display

/**
 * The UiHelper class
 * Please put more info here.
 * @author wafer
 * @since 16/10/27 23:14
 */


/**
 * Get screen size dp
 *
 * @return Pair of width and height in dp
 */
fun getScreenSizeDp(activity: Activity): Pair<Float, Float> {
    val display: Display = activity.windowManager.defaultDisplay
    val displayMetrics = DisplayMetrics()
    display.getMetrics(displayMetrics)

    val density = activity.resources.displayMetrics.density

    return Pair(displayMetrics.widthPixels / density, displayMetrics.heightPixels / density)
}