package com.pablojmuratore.redditposts.repositories

import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.network.RedditApi
import com.pablojmuratore.redditposts.network.RedditPostsNetworkEntityMapper

class RemoteDataRepository(private val redditPostsNetworkEntityMapper: RedditPostsNetworkEntityMapper) : IRemoteDataRepository {
    override suspend fun getTopPosts(): List<RedditPost> {
        return redditPostsNetworkEntityMapper.mapFromEntitiesList(RedditApi.getTopPosts())
    }
}