package com.wafer.toy.githubclient

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.wafer.toy.githubclient.network.ApiManager
import com.wafer.toy.githubclient.network.Trending
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.TestSubscriber
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.`is`
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
    fun testTrending() {

        val testSubscriber = TestSubscriber<Response<ResponseBody>>()

        trending.getTrending(since = "monthly")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(testSubscriber)

        testSubscriber.awaitTerminalEvent()

        testSubscriber.assertComplete().assertNoErrors()
        assertThat(testSubscriber.values().all { it.isSuccessful && it.body().string().contains("Trending  repositories on GitHub this month") }, `is`(true))
    }
}