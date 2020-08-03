package com.pablojmuratore.redditposts.repositories

import androidx.paging.DataSource
import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.room.AppDatabase
import com.pablojmuratore.redditposts.room.RedditPostDbEntityMapper

class LocalDataRepository(private val database: AppDatabase, private val redditPostDbEntityMapper: RedditPostDbEntityMapper) : ILocalDataRepository {
    override suspend fun getAllPosts(): List<RedditPost> {
        return redditPostDbEntityMapper.mapFromEntitiesList(database.redditPostsDao().getAll())
    }

    override fun getAllPostsPaged(): DataSource.Factory<Int, RedditPost> {
        return database.redditPostsDao().getAllPaged().map { post -> redditPostDbEntityMapper.mapFromEntity(post) }
    }

    override suspend fun savePosts(posts: List<RedditPost>) {
        database.redditPostsDao().savePosts(redditPostDbEntityMapper.mapToEntitiesList(posts))
    }

    override suspend fun clearPosts() {
        database.redditPostsDao().clearPosts()
    }
}