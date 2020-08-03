package com.pablojmuratore.redditposts.screens

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.pablojmuratore.redditposts.R
import com.pablojmuratore.redditposts.network.RedditPostNetworkEntityMapper
import com.pablojmuratore.redditposts.repositories.LocalDataRepository
import com.pablojmuratore.redditposts.repositories.PostsRepository
import com.pablojmuratore.redditposts.repositories.RemoteDataRepository
import com.pablojmuratore.redditposts.room.AppDatabase
import com.pablojmuratore.redditposts.room.RedditPostDbEntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getInstance()
            val remoteDataRepository = RemoteDataRepository(RedditPostNetworkEntityMapper())
            val localDataRepository = LocalDataRepository(database, RedditPostDbEntityMapper())
            val postsRepository = PostsRepository(remoteDataRepository, localDataRepository)

            Log.d("---x", "getting posts")
            val posts = postsRepository.getTopPosts(true)

            Log.d("---x", "posts retrieved: ${posts.size}")
        }
    }
}
