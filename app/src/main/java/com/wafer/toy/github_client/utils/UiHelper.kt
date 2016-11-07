package com.wafer.toy.github_client.utils

import android.app.Activity
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.util.DisplayMetrics
import android.util.TypedValue
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

fun getScreenSizePx(activity: Activity): Pair<Int, Int> {
    val display: Display = activity.windowManager.defaultDisplay
    val displayMetrics = DisplayMetrics()
    display.getMetrics(displayMetrics)
    return Pair(displayMetrics.widthPixels, displayMetrics.heightPixels)
}

fun getActionBarSize(activity: Activity): Int? {
    val tv = TypedValue()
    if (activity.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        return TypedValue.complexToDimensionPixelSize(tv.data, activity.resources.displayMetrics)
    }

    return null
}

fun getTintDefaultProfileIcon(activity: Activity): LayerDrawable {

    val res = activity.resources

    val background = ShapeDrawable()
    background.paint.color = res.getColor(R.color.default_icon_bg, activity.theme)

    val icon = res.getDrawable(R.drawable.default_user_icon, activity.theme)

    return LayerDrawable(arrayOf(background, icon))
}
