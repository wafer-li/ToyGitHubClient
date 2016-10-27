package com.wafer.toy.github_client.model.github_entity

import java.util.*

/**
 * The User data class for Gson converter
 *
 * NOTE THAT: **Organization is also a USER**
 *
 * @param login The login username of user
 *
 * @param id The user id of GitHub
 *
 * @param type The string to determine if the User is a Organization
 *
 * @param siteAdmin The boolean to illustrate if the user is the GitHub admin
 *
 * @param name The **FULL NAME** of the user
 *
 * @param company The company which hires the user now
 *
 * @param blog The user's personal site or blog
 *
 * @param location The location which is the current working position of the user
 *
 * @param email The public email of the user
 *
 * @param hireable Is the user hireable now?
 *
 * @param bio The personal experience of the user
 *
 * @param publicRepos The public repos count of the user
 *
 * @param publicGists The public gists count of the user
 *
 * @param followers How many followers the user have
 *
 * @param following How many people the user is following
 *
 * @param createdAt The Date Time of the user created
 *
 * @param updatedAt The Date Time of the user updated
 *
 *
 *
 * @param htmlUrl Hypermedia. The html page of user profile
 *
 * @param avatarUrl Hypermedia. The avatar url of the user(logo icon)
 *
 * @param followersUrl Hypermedia. The url to get the followers
 *
 * @param followingUrl Hypermedia. The url to get the people which this user following
 *
 * @param organizationsUrl Hypermedia. The url of the org which this user belongs to. Note that the
 * org is also a **USER**
 *
 * @param reposUrl Hypermedia. The url to get the repos of this user
 *
 * @param starredUrl Hypermedia. The url to get the repos which this user starred.
 */
data class User(
        val gistsUrl: String? = null,
        val reposUrl: String? = null,
        val followingUrl: String? = null,
        val bio: String? = null,
        val createdAt: Date?,
        val login: String? = null,
        val type: String? = null,
        val blog: String? = null,
        val subscriptionsUrl: String? = null,
        val updatedAt: Date?,
        val siteAdmin: Boolean?,
        val company: String? = null,
        val id: Int?,
        val publicRepos: Int?,
        val gravatarId: String? = null,
        val email: String? = null,
        val organizationsUrl: String? = null,
        val hireable: Boolean?,
        val starredUrl: String? = null,
        val followersUrl: String? = null,
        val publicGists: Int?,
        val url: String? = null,
        val receivedEventsUrl: String? = null,
        val followers: Int?,
        val avatarUrl: String? = null,
        val eventsUrl: String? = null,
        val htmlUrl: String? = null,
        val following: Int?,
        val name: String? = null,
        val location: String?
               )
