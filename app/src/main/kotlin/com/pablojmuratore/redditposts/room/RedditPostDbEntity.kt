package com.pablojmuratore.redditposts.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "redditposts")
data class RedditPostDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "created_utc")
    val createdUtc: Long,

    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "num_comments")
    val numComments: Long,

    @ColumnInfo(name = "read")
    var read: Boolean = false
)
