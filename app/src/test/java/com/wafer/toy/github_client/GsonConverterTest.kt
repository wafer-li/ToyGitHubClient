package com.wafer.toy.github_client

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.wafer.toy.github_client.model.ValidationError
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * The Gson Converter Test
 * Test the Gson Converter behavior
 */
class GsonConverterTest {

    private val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    /**
     * Test Gson Converter behavior of data class partly empty
     *
     * Test Class: [ValidationError]
     * Empty field: [ValidationError.field] [ValidationError.resource]
     *
     * Assertion: The class is NOT NULL,
     *            The class field which has data, is NOT NULL
     *            The class field which don't have data, will be NULL
     *
     * Result: Test PASS
     *
     * The field which didn't appear in json string,
     * will be NULL
     * (Not the empty string "")
     */
    @Test
    fun testGsonConvertPartlyEmpty() {
        val json: String = "{\"message\":\"error_test\", code:\"custom\"}"

        val validationError = gson.fromJson(json, ValidationError::class.java)

        assertThat(validationError, not(nullValue()))
        assertThat(validationError.code, not(nullValue()))
        assertThat(validationError.message, not(nullValue()))
        assertThat(validationError.field, `is`(nullValue()))
        assertThat(validationError.resource, `is`(nullValue()))
    }

    /**
     * Test Gson Converter with data totally empty
     *
     * Test Class: [ValidationError]
     *
     * Assertion: The data class is NOT NULL
     *
     * Result: PASS
     *
     * That means even the total data is empty,
     * the class itself WON'T BE NULL
     *
     */
    @Test
    fun testGsonConverterTotallyEmpty() {
        val json: String = "{}"

        val validationError = gson.fromJson(json, ValidationError::class.java)

        assertThat(validationError, not(nullValue()))
    }

    /**
     * Test Gson Converter with NULL json string
     *
     * Test Class: [ValidationError]
     *
     * Assertion: The data class is NULL
     *
     * Result: PASS
     *
     * That means if the json string is NULL,
     * the data class will be NULL
     */
    @Test
    fun testGsonConverterNull() {
        val json: String? = null

        val validationError = gson.fromJson(json, ValidationError::class.java)

        assertThat(validationError, `is`(nullValue()))
    }
}
