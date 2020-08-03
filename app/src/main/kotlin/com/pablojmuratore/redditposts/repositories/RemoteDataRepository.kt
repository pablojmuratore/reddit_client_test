package com.pablojmuratore.redditposts.repositories

import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.network.RedditApi
import com.pablojmuratore.redditposts.network.RedditPostNetworkEntityMapper

class RemoteDataRepository(private val redditPostsNetworkEntityMapper: RedditPostNetworkEntityMapper) : IRemoteDataRepository {
    override suspend fun getTopPosts(): List<RedditPost> {
        return redditPostsNetworkEntityMapper.mapFromEntitiesList(RedditApi.getTopPosts())
    }
}