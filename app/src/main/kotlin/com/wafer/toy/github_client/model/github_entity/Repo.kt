package com.wafer.toy.github_client.model.github_entity

import java.util.*

/**
 * The repo data class for gson converter
 *
 * @param id The id of this repo
 *
 * @param owner The owner of this repo, can be User or Org
 *
 * @param name The name of this repo
 *
 * @param fullName The full name of this repo, simply `/:owner/:name`
 *
 * @param description The description of this repo
 *
 * @param private Is the repo private?
 *
 * @param fork Is the repo has fork?
 *
 * @param homepage Home page of this repo
 *
 * @param language Languages which this repo uses
 *
 * @param forksCount The count of forks
 *
 * @param stargazersCount The count of stars
 *
 * @param watchersCount The count of watchers
 *
 * @param size The size of this repo
 *
 * @param defaultBranch Default branch of this repo (Normally is master)
 *
 * @param openIssueCount The count of the open issue
 *
 * @param hasIssues Is this repo has issue?
 *
 * @param hasWiki Is this repo has wiki?
 *
 * @param hasPages Is this repo has pages?
 *
 * @param hasDownloads Is this repo has downloads?
 *
 * @param pushedAt Date Time of the last push. Update when any **commit** was pushed at any **branches**
 *
 * @param updatedAt Date Time of the **repo object itself** was update.
 * Such as description or the primary language was updated
 *
 * @param createdAt Date Time when the repo was created
 *
 * @param permissions The permission of a org repo
 */
data class Repo(val id: Long, val owner: User, val name: String, val fullName: String,

                val description: String, val private: Boolean, val fork: Boolean,

                val homepage: String, val language: String,

                val forksCount: Int, val stargazersCount: Int, val watchersCount: Int,

                val size: Long, val defaultBranch: String, val openIssueCount: Int,

                val hasIssues: Boolean, val hasWiki: Boolean, val hasPages: Boolean,

                val hasDownloads: Boolean,

                val pushedAt: Date, val createdAt: Date, val updatedAt: Date,

                val permissions: Permissions)

