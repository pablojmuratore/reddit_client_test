package com.pablojmuratore.redditposts.repositories

import com.pablojmuratore.redditposts.model.RedditPost

interface IRemoteDataRepository {
    suspend fun getTopPosts(): List<RedditPost>
}