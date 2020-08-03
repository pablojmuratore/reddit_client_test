package com.pablojmuratore.redditposts.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pablojmuratore.redditposts.RedditPostsApplication

@Database(entities = [RedditPostDbEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var instance_: AppDatabase? = null

        fun getInstance(): AppDatabase {
            synchronized(AppDatabase::class) {
                if (instance_ == null) {
                    instance_ = buildDatabase()
                }
            }

            return instance_!!
        }

        private fun buildDatabase(): AppDatabase {
            return Room.databaseBuilder(RedditPostsApplication.getContext(), AppDatabase::class.java, "RedditPostsDb").build()
        }
    }

    abstract fun redditPostsDao(): RedditPostDao
}