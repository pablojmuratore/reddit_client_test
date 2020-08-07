package com.pablojmuratore.redditposts.repositories

import androidx.paging.DataSource
import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.room.AppDatabase
import com.pablojmuratore.redditposts.room.RedditPostDbEntityMapper
import javax.inject.Inject

class LocalDataRepository
@Inject
constructor(private val database: AppDatabase, private val redditPostDbEntityMapper: RedditPostDbEntityMapper) : ILocalDataRepository {
    override suspend fun getAllPosts(): List<RedditPost> {
        return redditPostDbEntityMapper.mapFromEntitiesList(database.redditPostsDao().getAll())
    }

    override suspend fun getPostById(redditPostId: String): RedditPost {
        return redditPostDbEntityMapper.mapFromEntity(database.redditPostsDao().getPostById(redditPostId))
    }

    override fun getAllPostsPaged(): DataSource.Factory<Int, RedditPost> {
        return database.redditPostsDao().getAllPaged().map { post -> redditPostDbEntityMapper.mapFromEntity(post) }
    }

    override suspend fun markPostRead(redditPostId: String) {
        database.redditPostsDao().markPostRead(redditPostId)
    }

    override suspend fun savePosts(posts: List<RedditPost>) {
        database.redditPostsDao().savePosts(redditPostDbEntityMapper.mapToEntitiesList(posts))
    }

    override suspend fun deletePost(redditPostId: String) {
        database.redditPostsDao().deletePost(redditPostId)
    }

    override suspend fun clearPosts() {
        database.redditPostsDao().clearPosts()
    }
}