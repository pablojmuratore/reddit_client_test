package com.pablojmuratore.redditposts

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RedditPostsApplication : MultiDexApplication() {
    companion object {
        private lateinit var redditPostsApplication: RedditPostsApplication

        fun getContext(): RedditPostsApplication {
            return redditPostsApplication
        }
    }

    override fun onCreate() {
        super.onCreate()

        redditPostsApplication = this
    }


}