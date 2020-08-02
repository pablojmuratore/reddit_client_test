package com.pablojmuratore.redditposts.model

data class RedditPost(
    val author: String,
    val createdUtc: Long,
    val thumbnail: String,
    val title: String,
    val numComments: Long,
    var read: Boolean = false
)
