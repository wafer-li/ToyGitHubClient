package com.wafer.toy.githubclient

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.wafer.toy.githubclient.model.network.Repo
import com.wafer.toy.githubclient.model.network.TrendingCard
import com.wafer.toy.githubclient.model.network.User
import com.wafer.toy.githubclient.network.ApiManager
import com.wafer.toy.githubclient.network.Trending
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response

/**
 * The TrendingApiTest
 * Please put more info here.
 * @author wafer
 * @since 17/4/30 21:09
 */
@RunWith(AndroidJUnit4::class)
class TrendingApiTest {

    lateinit var trending: Trending
    lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getTargetContext()
        trending = ApiManager.apply { init(context) }.createTrendingService(Trending::class.java)
    }

    @Test
    fun testTrendingWithJsoup() {

        val testSubscriber = TestObserver<TrendingCard>()

        ApiManager.createTrendingService(Trending::class.java)
                .getTrending(since = "daily")
                .subscribeOn(Schedulers.io())
                .filter { it.isSuccessful }
                .flatMap {
                    // Map the HTML source to Repos
                    Observable.fromIterable(Jsoup.parse(it.body().string()).select("ol.repo-list").first().children())
                }
                .map {
                    // `it` is the repo-list item, particularly <li>
                    val repoAElement = it.select("h3 > a").first()
                    val repoLink = repoAElement.attr("href")

                    val repoTitle = repoAElement.text()
                    val description = it.select(".py-1 > p").first()?.ownText()
                    val lang = it.select("""[itemprop="programmingLanguage"]""").first()?.text()
                    val stars = it.select("a[href=\"$repoLink/stargazers\"]").first().text().filter { it.isDigit() }.toInt()
                    val forks = it.select("a[href=\"$repoLink/network\"]").first().text().filter { it.isDigit() }.toInt()

                    val contributors = it.select("a[href=$repoLink/graphs/contributors]").first()
                            ?.children()
                            ?.map {
                                // it is the contributor's avatar
                                val userName = it.attr("alt").apply { drop(1) }
                                val avatarUrl = it.attr("src")
                                User(userName = userName, avatarUrl = avatarUrl)
                            } ?: listOf()

                    val starsTimeInterval = it.select("span.float-right").first().text()
                            .filterNot { it == ',' }

                    val repo = Repo(fullName = repoTitle,
                            name = repoTitle.split("/")[1],
                            description = description,
                            language = lang,
                            stargazersCount = stars,
                            forksCount = forks)

                    TrendingCard(repo, contributors, starsTimeInterval)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(testSubscriber)

        testSubscriber.awaitTerminalEvent()

        testSubscriber.assertComplete().assertNoErrors()

        assertThat(testSubscriber.valueCount(), `is`(25))

        assertEquals(true,
                testSubscriber.values().all {
                    it.repo.run {
                        !fullName.isNullOrBlank() &&
                                !name.isNullOrBlank() &&
                                stargazersCount != null && stargazersCount != 0 &&
                                forksCount != null && forksCount != 0
                    } &&
                            (it.contributors.any {
                                it.run {
                                    !userName.isNullOrBlank() &&
                                            userName!!.contains("@") &&
                                            !avatarUrl.isNullOrBlank()
                                }
                            } || it.contributors.isEmpty())
                }
        )
    }

    @Test
    fun testTrending() {

        val testObserver = TestObserver<Response<ResponseBody>>()

        trending.getTrending(since = "monthly")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(testObserver)

        testObserver.awaitTerminalEvent()

        testObserver.assertComplete().assertNoErrors()
        assertThat(
                testObserver.values().all {
                    it.isSuccessful && it.body().string().contains("Trending  repositories on GitHub this month")
                },
                `is`(true)
        )

    }
}