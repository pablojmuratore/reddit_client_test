package com.pablojmuratore.redditposts.room

import androidx.paging.DataSource
import androidx.room.*
import com.pablojmuratore.redditposts.model.RedditPost

@Dao
interface RedditPostDao {
    @Query("select * from redditposts")
    suspend fun getAll(): List<RedditPostDbEntity>

    @Query("select * from redditposts where id=:redditPostId")
    suspend fun getPostById(redditPostId: String): RedditPostDbEntity

    @Query("select * from redditposts")
    fun getAllPaged(): DataSource.Factory<Int, RedditPostDbEntity>

    @Query("update redditposts set read=1 where id=:redditPostId")
    suspend fun markPostRead(redditPostId: String)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun savePosts(posts: List<RedditPostDbEntity>)

    @Query("delete from redditposts where id=:redditPostId")
    suspend fun deletePost(redditPostId: String)

    @Query("delete from redditposts")
    suspend fun clearPosts()

}