package com.wafer.toy.githubclient.model.network

import com.google.gson.annotations.SerializedName

data class SearchResponse<out T>(

        @field:SerializedName("total_count")
        val totalCount: Int? = null,

        @field:SerializedName("incomplete_results")
        val incompleteResults: Boolean? = null,

        @field:SerializedName("items")
        val items: List<T?>? = null
)

