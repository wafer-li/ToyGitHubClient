package com.wafer.toy.githubclient.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafer.toy.githubclient.R
import com.wafer.toy.githubclient.model.network.Repo
import com.wafer.toy.githubclient.model.network.TrendingCard
import com.wafer.toy.githubclient.model.network.User
import kotlinx.android.synthetic.main.repo_card.view.*
import kotlinx.android.synthetic.main.repo_description.view.*

/**
 * The TrendingContentAdapter
 * Please put more info here.
 * @author wafer
 * @since 17/4/30 15:41
 */


class TrendingContentAdapter(val trendingCards: MutableList<TrendingCard>)
    : RecyclerView.Adapter<TrendingContentAdapter.ViewHolder>() {

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        fun bindRepoInfo(repo: Repo) {
            itemView.repo_name.text = repo.fullName

            if (!repo.description.isNullOrEmpty()) {
                itemView.repo_descprition_stub?.run { inflate() }
                itemView.repo_descprition.text = repo.description
            }

            itemView.language.text = repo.language
            itemView.star_count.text = repo.stargazersCount.toString()
            itemView.fork_count.text = repo.forksCount.toString()
        }

        fun bindContributors(contributors: List<User>) {
            // TODO Need Image lib
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindRepoInfo(trendingCards[position].repo)
        holder?.bindContributors(trendingCards[position].contributors)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.repo_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = trendingCards.size

}
