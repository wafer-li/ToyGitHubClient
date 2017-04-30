package com.wafer.toy.githubclient.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.wafer.toy.githubclient.R
import com.wafer.toy.githubclient.model.network.User
import kotlinx.android.synthetic.main.repo_card_contributor.view.*

/**
 * The TrendingContributorAdapter
 * Please put more info here.
 * @author wafer
 * @since 17/5/1 02:25
 */
class TrendingContributorAdapter(val contributors: List<User>)
    : RecyclerView.Adapter<TrendingContributorAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindImage(contributor: User) {
            itemView.contributor_avatar.contentDescription =
                    itemView.context.resources.getString(R.string.content_des_avatar_icon)
                            .format(contributor.userName)

            Glide.with(itemView.context)
                    .load(contributor.avatarUrl)
                    .into(itemView.contributor_avatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.repo_card_contributor, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindImage(contributors[position])
    }

    override fun getItemCount(): Int = contributors.size

}