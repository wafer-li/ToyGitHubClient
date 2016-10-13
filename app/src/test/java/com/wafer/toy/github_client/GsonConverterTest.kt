package com.wafer.toy.github_client

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class GsonConverterTest {

    /**
     * Test Gson Converter behavior of validation error
     *
     * Result: Test PASS.
     *
     * The field which didn't appear in json string,
     * will be NULL
     * (Not the empty string "")
     */
    @Test
    fun testGsonConvertPartlyEmpty() {
        val gson: Gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

        val json: String = "{\"message\":\"error_test\", code:\"custom\"}"

        val validationError = gson.fromJson(json, ValidationError::class.java)

        assertThat(validationError, not(nullValue()))
        assertThat(validationError.code, not(nullValue()))
        assertThat(validationError.message, not(nullValue()))
        assertThat(validationError.field, `is`(nullValue()))
        assertThat(validationError.resource, `is`(nullValue()))
    }
}
