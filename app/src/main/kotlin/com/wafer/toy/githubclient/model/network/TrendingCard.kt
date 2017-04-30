package com.wafer.toy.githubclient.model.network

/**
 * The TrendingCard
 * Please put more info here.
 * @author wafer
 * @since 17/4/30 23:40
 */
data class TrendingCard(val repo: Repo, val contributors: List<User>, val starsTimeInterval: String)
