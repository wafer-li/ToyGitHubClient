package com.wafer.toy.github_client.model.github_entity

import java.util.*

/**
 * The repo data class for gson converter
 *
 * @author wafer
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
 * @param openIssuesCount The count of the open issue
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
data class Repo(
        val stargazersCount: Int?,
        val pushedAt: Date?,
        val subscriptionUrl: String?,
        val language: Any?,
        val branchesUrl: String?,
        val issueCommentUrl: String?,
        val labelsUrl: String?,
        val subscribersUrl: String?,
        val permissions: Permissions?,
        val releasesUrl: String?,
        val svnUrl: String?,
        val id: Int?,
        val archiveUrl: String?,
        val gitRefsUrl: String?,
        val forksUrl: String?,
        val statusesUrl: String?,
        val sshUrl: String?,
        val fullName: String?,
        val size: Int?,
        val languagesUrl: String?,
        val htmlUrl: String?,
        val cloneUrl: String?,
        val collaboratorsUrl: String?,
        val name: String?,
        val pullsUrl: String?,
        val defaultBranch: String?,
        val hooksUrl: String?,
        val treesUrl: String?,
        val tagsUrl: String?,
        val private: Boolean?,
        val contributorsUrl: String?,
        val hasDownloads: Boolean?,
        val notificationsUrl: String?,
        val openIssuesCount: Int?,
        val description: String?,
        val createdAt: Date?,
        val deploymentsUrl: String?,
        val keysUrl: String?,
        val hasWiki: Boolean?,
        val updatedAt: Date?,
        val commentsUrl: String?,
        val stargazersUrl: String?,
        val gitUrl: String?,
        val hasPages: Boolean?,
        val owner: User?,
        val commitsUrl: String?,
        val compareUrl: String?,
        val gitCommitsUrl: String?,
        val blobsUrl: String?,
        val gitTagsUrl: String?,
        val mergesUrl: String?,
        val downloadsUrl: String?,
        val hasIssues: Boolean?,
        val url: String?,
        val contentsUrl: String?,
        val mirrorUrl: String?,
        val milestonesUrl: String?,
        val teamsUrl: String?,
        val fork: Boolean?,
        val issuesUrl: String?,
        val eventsUrl: String?,
        val issueEventsUrl: String?,
        val assigneesUrl: String?,
        val watchersCount: Int?,
        val homepage: String?,
        val forksCount: Int?
)
