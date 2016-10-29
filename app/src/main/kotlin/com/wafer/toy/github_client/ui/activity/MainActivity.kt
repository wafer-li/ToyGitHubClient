package com.wafer.toy.github_client.ui.activity

import android.support.v7.app.ActionBarDrawerToggle
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.wafer.toy.github_client.R
import com.wafer.toy.github_client.utils.getUserNameAndLogoUrl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val drawer: Drawer by lazy { initDrawer() }
    private val accountHeader: AccountHeader by lazy { initAccountHeader() }
    private val profileItem: ProfileDrawerItem by lazy { initProfileItem() }

    override fun initView() {
        initToolbar()
        drawer
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    private fun initDrawer(): Drawer {
        return DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(accountHeader)
                .build()
    }

    private fun initAccountHeader(): AccountHeader {
        return AccountHeaderBuilder()
                .withActivity(this)
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles(profileItem)
                .withHeaderBackground(R.color.primary)
                .build()
    }

    private fun initProfileItem(): ProfileDrawerItem {
        val nameAndLogoUrl = getUserNameAndLogoUrl(this)

        if (nameAndLogoUrl != null) {
            return ProfileDrawerItem()
                    .withName(nameAndLogoUrl.first)
                    .withIcon(nameAndLogoUrl.second)
        } else {
            return ProfileDrawerItem()
                    .withName(getString(R.string.unknown_user))
                    .withEmail(getString(R.string.click_to_login))
                    .withIcon(R.drawable.default_user_icon)
        }
    }
}
