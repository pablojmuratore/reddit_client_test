package com.pablojmuratore.redditposts.repositories

import com.pablojmuratore.redditposts.model.RedditPost

interface ILocalDataRepository {
    suspend fun getAllPosts(): List<RedditPost>
    suspend fun savePosts(posts: List<RedditPost>)
    suspend fun clearPosts()
}