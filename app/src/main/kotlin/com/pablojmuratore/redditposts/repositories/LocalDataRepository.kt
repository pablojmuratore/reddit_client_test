package com.pablojmuratore.redditposts.repositories

import android.util.Log
import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.room.AppDatabase
import com.pablojmuratore.redditposts.room.RedditPostDbEntityMapper

class LocalDataRepository(private val database: AppDatabase, private val redditPostDbEntityMapper: RedditPostDbEntityMapper) : ILocalDataRepository {
    override suspend fun getAllPosts(): List<RedditPost> {
        Log.d("---x", "getAllPosts ( LocalDataRepository )")

        return redditPostDbEntityMapper.mapFromEntitiesList(database.redditPostsDao().getAll())
    }

    override suspend fun savePosts(posts: List<RedditPost>)  {
        Log.d("---x", "savePosts ( LocalDataRepository )")
        database.redditPostsDao().savePosts(redditPostDbEntityMapper.mapToEntitiesList(posts))
    }

    override suspend fun clearPosts() {
        database.redditPostsDao().clearPosts()
    }
}