package com.wafer.toy.github_client.utils

import android.content.Context
import android.content.SharedPreferences
import com.wafer.toy.github_client.constants.FULL_NAME_KEY
import com.wafer.toy.github_client.constants.OAUTH_KEY
import com.wafer.toy.github_client.constants.SHARED_PREFERENCE_KEY
import com.wafer.toy.github_client.constants.USER_LOGO_KEY

/**
 * The SharedPreferenceHelper class
 * Please put more info here.
 * @author wafer
 * @since 16/10/26 20:01
 */

fun getEditor(context: Context): SharedPreferences.Editor {
    return context.applicationContext
            .getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)
            .edit()
}


fun getUserNameAndLogoUrl(context: Context): Pair<String?, String?>? {

    if (getOAuthToken(context) != "") {
        val preference = context.applicationContext
                .getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)
        return Pair(preference.getString(FULL_NAME_KEY, null), preference.getString(USER_LOGO_KEY, null))
    } else {
        return null
    }
}


fun SharedPreferences.Editor.storeOAuthToken(oAuthToken: String): SharedPreferences.Editor {
    return putString(OAUTH_KEY, oAuthToken)
}

