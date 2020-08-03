package com.pablojmuratore.redditposts.repositories

import androidx.paging.DataSource
import com.pablojmuratore.redditposts.model.RedditPost

interface ILocalDataRepository {
    suspend fun getAllPosts(): List<RedditPost>
    fun getAllPostsPaged(): DataSource.Factory<Int, RedditPost>
    suspend fun savePosts(posts: List<RedditPost>)
    suspend fun clearPosts()
}