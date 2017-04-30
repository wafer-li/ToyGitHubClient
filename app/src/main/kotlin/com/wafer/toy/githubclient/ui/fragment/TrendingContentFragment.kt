package com.wafer.toy.githubclient.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafer.toy.githubclient.R
import com.wafer.toy.githubclient.application.Constants
import com.wafer.toy.githubclient.model.network.Repo
import com.wafer.toy.githubclient.model.network.TrendingCard
import com.wafer.toy.githubclient.model.network.User
import com.wafer.toy.githubclient.network.ApiManager
import com.wafer.toy.githubclient.network.Trending
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup


/**
 * The TrendingContentFragment class
 * Please put more info here.
 * @author wafer
 * @since 17/4/29 20:02
 */
class TrendingContentFragment : Fragment() {

    private lateinit var pageTitle: String
    private lateinit var trendingTitles: Array<out String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageTitle = arguments.getString(Constants.PAGE_TITLE)
        trendingTitles = resources.getStringArray(R.array.trending_tab_titles)

        val since = getSinceParam(trendingTitles.indexOf(pageTitle))

        ApiManager.createTrendingService(Trending::class.java)
                .getTrending(since = since)
                .subscribeOn(Schedulers.io())
                .filter { it.isSuccessful }
                .flatMap {
                    // Map the HTML source to Repos
                    Flowable.fromIterable(Jsoup.parse(it.body().string()).select("ol.repo-list"))
                }
                .map {
                    // `it` is the repo-list item, particularly <li>
                    val repoAElement = it.select("h3 > a").first()
                    val repoLink = repoAElement.attr("href")

                    val repoTitle = repoAElement.text()
                    val description = it.select(".py-1 > p").first().ownText()
                    val lang = it.select("[itemprop=programmingLanguage]").first().text()
                    val stars = it.select("a[href=$repoLink/stargazers]").first().text().filter { it.isDigit() }.toInt()
                    val forks = it.select("a[href=$repoLink/network]").first().text().filter { it.isDigit() }.toInt()

                    val contributors = it.select("a[href=$repoLink/graphs/contributors]").first()
                            .children()
                            .map {
                                // it is the contributor's avatar
                                val userName = it.attr("alt").apply { drop(1) }
                                val avatarUrl = it.attr("src")
                                User(userName = userName, avatarUrl = avatarUrl)
                            }

                    val repo = Repo(
                            fullName = repoTitle,
                            name = repoTitle.split("/")[1],
                            description = description,
                            language = lang,
                            stargazersCount = stars,
                            forksCount = forks)

                    TrendingCard(repo, contributors)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {}
    }

    private fun getSinceParam(index: Int): String =
            when (index) {
                1 -> "weekly"
                2 -> "monthly"
                else -> "daily"
            }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.content_main, container, false)

        return view
    }
}