package com.pablojmuratore.redditposts.network

data class TopPostsResponse(
    val data: TopPostsResponseData
)

data class TopPostsResponseData(
    val children: List<RedditPostNetworkEntity>
)

