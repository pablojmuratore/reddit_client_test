package com.pablojmuratore.redditposts.model

import java.util.*

data class RedditPost(
    val author: String,
    val createdUtc: Date,
    val thumbnail: String,
    val title: String,
    val numComments: Long,
    var read: Boolean = false
)
