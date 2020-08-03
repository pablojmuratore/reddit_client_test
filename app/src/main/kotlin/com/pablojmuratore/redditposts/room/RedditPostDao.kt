package com.pablojmuratore.redditposts.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pablojmuratore.redditposts.model.RedditPost

@Dao
interface RedditPostDao {
    @Query("select * from redditposts")
    suspend fun getAll(): List<RedditPostDbEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun savePosts(posts: List<RedditPostDbEntity>)

    @Query("delete from redditposts")
    suspend fun clearPosts()

}