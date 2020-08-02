package com.pablojmuratore.redditposts.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pablojmuratore.redditposts.R
import com.pablojmuratore.redditposts.network.RedditPostsNetworkEntityMapper
import com.pablojmuratore.redditposts.repositories.PostsRepository
import com.pablojmuratore.redditposts.repositories.RemoteDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {
            val remoteDataRepository = RemoteDataRepository(RedditPostsNetworkEntityMapper())
            val postsRepository = PostsRepository(remoteDataRepository)

            val posts = postsRepository.getTopPosts()

            Log.d("---x", "posts: ${posts.size}")
        }
    }
}

