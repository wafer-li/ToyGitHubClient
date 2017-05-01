package com.wafer.toy.githubclient.ui.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafer.toy.githubclient.R
import com.wafer.toy.githubclient.model.network.Repo
import com.wafer.toy.githubclient.model.network.TrendingCard
import com.wafer.toy.githubclient.model.network.User
import kotlinx.android.synthetic.main.repo_card.view.*

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

            repo.description?.let {
                itemView.repo_descprition.visibility = View.VISIBLE
                itemView.repo_descprition.text = it
            }

            itemView.language.text = repo.language
            itemView.star_count.text = repo.stargazersCount.toString()
            itemView.fork_count.text = repo.forksCount.toString()
        }

        fun bindContributors(contributors: List<User>) {
            if (contributors.isNotEmpty()) {
                val adapter = TrendingContributorAdapter(contributors)
                itemView.authors_recycler.layoutManager =
                        LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

                itemView.authors_recycler.adapter = adapter
            } else {
                itemView.authors_recycler.visibility = View.GONE
                itemView.built_by_text.visibility = View.GONE
            }
        }

        fun bindStarsTimeInterval(starsTimeInterval: String) {
            itemView.stars_time_interval.text = starsTimeInterval
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindRepoInfo(trendingCards[position].repo)
        holder?.bindContributors(trendingCards[position].contributors)
        holder?.bindStarsTimeInterval(trendingCards[position].starsTimeInterval)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.repo_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = trendingCards.size

}
