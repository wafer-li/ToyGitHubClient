package com.wafer.toy.githubclient.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
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
import com.wafer.toy.githubclient.ui.adapter.TrendingContentAdapter
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.content_main.*
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

    private val trendingCards = mutableListOf<TrendingCard>()
    private val trendingContentAdapter = TrendingContentAdapter(trendingCards)

    private var isLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageTitle = arguments.getString(Constants.PAGE_TITLE)
        trendingTitles = resources.getStringArray(R.array.trending_tab_titles)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.content_main, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = trendingContentAdapter

        val since = getSinceParam(trendingTitles.indexOf(pageTitle))

        ApiManager.createTrendingService(Trending::class.java)
                .getTrending(since = since)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { it.isSuccessful }
                .flatMap {
                    // Map the HTML source to Repos
                    Observable.fromIterable(
                            Jsoup.parse(it.body().string()).select("ol.repo-list").first().children())
                }
                .map {
                    // `it` is the repo-list item, particularly <li>
                    val repoAElement = it.select("h3 > a").first()
                    val repoLink = repoAElement.attr("href")

                    val repoTitle = repoAElement.text()

                    val description = it.select(".py-1 > p").first()?.ownText()
                    val lang = it.select("""[itemprop="programmingLanguage"]""")
                            .first()?.text() ?: getString(R.string.unknown)

                    val stars = it.select("""a[href="$repoLink/stargazers"]""").first().text()
                            .filter { it.isDigit() }.toInt()
                    val forks = it.select("""a[href="$repoLink/network"]""").first().text()
                            .filter { it.isDigit() }.toInt()

                    val contributors = it.select("a[href=$repoLink/graphs/contributors]").first()
                            .children()
                            .map {
                                // it is the contributor's avatar
                                val userName = it.attr("alt").apply { drop(1) }
                                val avatarUrl = it.attr("src")
                                User(userName = userName, avatarUrl = avatarUrl)
                            }

                    val starsTimeInterval = it.select("span.float-right").first().text()
                            .filterNot { it == ',' }

                    val repo = Repo(
                            fullName = repoTitle,
                            name = repoTitle.split("/")[1],
                            description = description,
                            language = lang,
                            stargazersCount = stars,
                            forksCount = forks)

                    TrendingCard(repo, contributors, starsTimeInterval)
                }
                .subscribe(object : Observer<TrendingCard> {
                    override fun onSubscribe(d: Disposable?) {
                        Log.d("Subscribe", "S!")

                        if (!isLoaded)
                            trendingCards.clear()

                    }

                    override fun onComplete() {
                        Log.d("Complete", "C!")
                        trendingContentAdapter.notifyDataSetChanged()
                        isLoaded = true
                    }

                    override fun onNext(t: TrendingCard?) {
                        Log.d("Next", "N!")
                        if (t != null && !isLoaded) {
                            trendingCards.add(t)
                        }
                    }

                    override fun onError(t: Throwable?) {
                        Log.d("ERROR", "E!")
                        t?.printStackTrace()
                    }
                })
    }

    private fun getSinceParam(index: Int): String =
            when (index) {
                1 -> "weekly"
                2 -> "monthly"
                else -> "daily"
            }
}