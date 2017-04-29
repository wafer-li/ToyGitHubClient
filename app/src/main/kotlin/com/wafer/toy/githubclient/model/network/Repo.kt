package com.wafer.toy.githubclient.model.network

import com.google.gson.annotations.SerializedName

data class Repo(

        @field:SerializedName("owner")
        val owner: User? = null,

        @field:SerializedName("private")
        val jsonMemberPrivate: Boolean? = null,

        @field:SerializedName("stargazers_count")
        val stargazersCount: Int? = null,

        @field:SerializedName("pushed_at")
        val pushedAt: String? = null,

        @field:SerializedName("open_issues_count")
        val openIssuesCount: Int? = null,

        @field:SerializedName("description")
        val description: String? = null,

        @field:SerializedName("created_at")
        val createdAt: String? = null,

        @field:SerializedName("language")
        val language: String? = null,

        @field:SerializedName("url")
        val url: String? = null,

        @field:SerializedName("score")
        val score: Double? = null,

        @field:SerializedName("fork")
        val fork: Boolean? = null,

        @field:SerializedName("full_name")
        val fullName: String? = null,

        @field:SerializedName("updated_at")
        val updatedAt: String? = null,

        @field:SerializedName("size")
        val size: Int? = null,

        @field:SerializedName("html_url")
        val htmlUrl: String? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("default_branch")
        val defaultBranch: String? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("watchers_count")
        val watchersCount: Int? = null,

        @field:SerializedName("master_branch")
        val masterBranch: String? = null,

        @field:SerializedName("homepage")
        val homepage: String? = null,

        @field:SerializedName("forks_count")
        val forksCount: Int? = null
)