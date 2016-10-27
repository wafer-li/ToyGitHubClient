package com.wafer.toy.github_client.utils

import android.app.Activity
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.util.DisplayMetrics
import android.view.Display
import com.wafer.toy.github_client.R

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

fun getTintDefaultProfileIcon(activity: Activity): LayerDrawable {

    val res = activity.resources

    val background = ShapeDrawable()
    background.paint.color = res.getColor(R.color.default_icon_bg, activity.theme)

    val icon = res.getDrawable(R.drawable.default_user_icon, activity.theme)

    return LayerDrawable(arrayOf(background, icon))
}
