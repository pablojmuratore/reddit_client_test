package com.pablojmuratore.redditposts.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RedditPostDao {
    @Query("select * from redditposts")
    suspend fun getAll(): List<RedditPostDbEntity>

    @Query("select * from redditposts where id=:redditPostId")
    suspend fun getPostById(redditPostId: String): RedditPostDbEntity

    @Query("select * from redditposts")
    fun getAllPaged(): DataSource.Factory<Int, RedditPostDbEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun savePosts(posts: List<RedditPostDbEntity>)

    @Query("delete from redditposts")
    suspend fun clearPosts()

}