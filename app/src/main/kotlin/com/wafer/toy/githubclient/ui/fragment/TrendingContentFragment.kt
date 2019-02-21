package com.wafer.toy.githubclient.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.wafer.toy.githubclient.R
import com.wafer.toy.githubclient.application.Constants
import com.wafer.toy.githubclient.model.network.Repo
import com.wafer.toy.githubclient.model.network.TrendingCard
import com.wafer.toy.githubclient.model.network.User
import com.wafer.toy.githubclient.network.ApiManager
import com.wafer.toy.githubclient.network.TrendingApi
import com.wafer.toy.githubclient.ui.adapter.TrendingContentAdapter
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.content_main.*
import org.jsoup.Jsoup
import retrofit2.HttpException
import kotlin.properties.Delegates


/**
 * The TrendingContentFragment class
 * Please put more info here.
 * @author wafer
 * @since 17/4/29 20:02
 */
class TrendingContentFragment : Fragment() {

    private lateinit var pageTitle: String
    private lateinit var trendingTitles: Array<out String>
    private lateinit var since: String
    private lateinit var observer: Observer<TrendingCard>

    private val trendingCards = mutableListOf<TrendingCard>()
    private val trendingContentAdapter = TrendingContentAdapter(trendingCards)

    private var isLoaded = false

    var isFragmentVisible by Delegates.observable(true) {
        _, _, visible ->

        if (visible)
            loadData(since, !isLoaded, observer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pageTitle = arguments?.getString(Constants.PAGE_TITLE) ?: ""
        trendingTitles = resources.getStringArray(R.array.trending_tab_titles)
        since = getSinceParam(trendingTitles.indexOf(pageTitle))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.content_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer = object : Observer<TrendingCard> {
            override fun onSubscribe(d: Disposable) {
                Log.d("onSubscribe", "S!")

                trendingCards.clear()
                trendingContentAdapter.notifyDataSetChanged()

                if (!swipe_refresh.isRefreshing)
                    swipe_refresh.isRefreshing = true
            }

            override fun onComplete() {
                Log.d("onComplete", "C!")

                trendingContentAdapter.notifyDataSetChanged()
                isLoaded = true

                if (swipe_refresh.isRefreshing)
                    swipe_refresh.isRefreshing = false
            }

            override fun onNext(t: TrendingCard) {
                Log.d("onNext", "N!")

                trendingCards.add(t)
            }

            override fun onError(t: Throwable) {
                Log.d("onERROR", "E!")
                t.printStackTrace()

                if (swipe_refresh.isRefreshing)
                    swipe_refresh.isRefreshing = false

                when (t) {
                    is HttpException -> {
                        Snackbar.make(recycler.rootView, "Network Error: ${t.code()} ${t.message()}",
                                Snackbar.LENGTH_LONG)
                                .show()
                    }
                    else ->
                        Snackbar.make(recycler.rootView, "Parse Error", Snackbar.LENGTH_LONG).show()
                }
            }
        }

        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = trendingContentAdapter

        swipe_refresh.setOnRefreshListener { loadData(since, true, observer) }

        if (isFragmentVisible)
            loadData(since, !isLoaded, observer)
    }

    private fun loadData(since: String, openLoad: Boolean, observer: Observer<TrendingCard>) {
        if (openLoad) {
            ApiManager.createTrendingService(TrendingApi::class.java)
                    .getTrending(since = since)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .flatMap {
                        // Map the HTML source to Repos
                        Observable.fromIterable(
                                Jsoup.parse(it.string()).select("ol.repo-list").first().children())
                    }
                    .map { repoItem ->
                        // repoItem is the repo-list item, particularly <li>
                        val repoAElement = repoItem.select("h3 > a").first()
                        val repoLink = repoAElement.attr("href")

                        val repoTitle = repoAElement.text()

                        val description = repoItem.select(".py-1 > p").first()?.ownText()
                        val lang = repoItem.select("""[itemprop="programmingLanguage"]""")
                                .first()?.text() ?: getString(R.string.unknown)

                        val stars = repoItem.select("""a[href="$repoLink/stargazers"]""").first().text()
                                .filter { it.isDigit() }.toInt()
                        val forks = repoItem.select("""a[href="$repoLink/network"]""").first().text()
                                .filter { it.isDigit() }.toInt()

                        val contributors = repoItem.select("span:containsOwn(Built By)").first()
                                ?.children()
                                ?.map {
                                    // it is the contributor avatar's link, i.e <a>
                                    val userName = it.child(0).attr("alt").apply { drop(1) }
                                    val avatarUrl = it.child(0).attr("src")
                                    User(userName = userName, avatarUrl = avatarUrl)
                                } ?: listOf()

                        val starsTimeInterval = repoItem.select("span.float-right").first()?.text()
                                ?.filterNot { it == ',' }

                        val repo = Repo(
                                fullName = repoTitle,
                                name = repoTitle.split("/")[1],
                                description = description,
                                language = lang,
                                stargazersCount = stars,
                                forksCount = forks)

                        TrendingCard(repo, contributors, starsTimeInterval)
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }
    }


    private fun getSinceParam(index: Int): String =
            when (index) {
                1 -> "weekly"
                2 -> "monthly"
                else -> "daily"
            }
}