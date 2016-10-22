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
* @param avatarUrl The avatar url of the user(logo icon)
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
* @param createdAt The Date Time of the user created
*
* @param updatedAt The Date Time of the user updated
*/
data class User(
	val gistsUrl: String?,
	val reposUrl: String?,
	val followingUrl: String?,
	val bio: String?,
	val createdAt: Date?,
	val login: String?,
	val type: String?,
	val blog: String?,
	val subscriptionsUrl: String?,
	val updatedAt: Date?,
	val siteAdmin: Boolean?,
	val company: String?,
	val id: Int?,
	val publicRepos: Int?,
	val gravatarId: String?,
	val email: String?,
	val organizationsUrl: String?,
	val hireable: Boolean?,
	val starredUrl: String?,
	val followersUrl: String?,
	val publicGists: Int?,
	val url: String?,
	val receivedEventsUrl: String?,
	val followers: Int?,
	val avatarUrl: String?,
	val eventsUrl: String?,
	val htmlUrl: String?,
	val following: Int?,
	val name: String?,
	val location: String?
)
