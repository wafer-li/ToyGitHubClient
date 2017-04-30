package com.wafer.toy.githubclient.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafer.toy.githubclient.R
import com.wafer.toy.githubclient.model.network.User

/**
 * The TrendingContributorAdapter
 * Please put more info here.
 * @author wafer
 * @since 17/5/1 02:25
 */
class TrendingContributorAdapter(val contributors: List<User>)
    : RecyclerView.Adapter<TrendingContributorAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindImage(contributors: List<User>) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.repo_card_contributor, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int = contributors.size

}