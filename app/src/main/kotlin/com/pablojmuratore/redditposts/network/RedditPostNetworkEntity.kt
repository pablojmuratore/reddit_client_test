package com.pablojmuratore.redditposts.network

import com.squareup.moshi.Json

data class RedditPostNetworkEntity(
    val data: RedditPostDataNetworkEntity
)

data class RedditPostDataNetworkEntity(
    val id: String,
    val author: String,
    @Json(name = "created_utc") val createdUtc: Long,
    val thumbnail: String,
    val title: String,
    @Json(name = "num_comments") val numComments: Long,
    @Json(name = "is_self") val isTextPost: Boolean,
    @Json(name = "url") val imageUrl: String
)
