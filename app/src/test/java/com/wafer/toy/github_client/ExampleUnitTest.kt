package com.wafer.toy.github_client

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumentation test, which will execute on an Android device.

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun testPlus() {
        assertEquals(1 + 1, 2)
    }
}
