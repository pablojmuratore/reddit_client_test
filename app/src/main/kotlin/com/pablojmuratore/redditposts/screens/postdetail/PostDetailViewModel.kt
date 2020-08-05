package com.pablojmuratore.redditposts.screens.postdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.network.RedditPostNetworkEntityMapper
import com.pablojmuratore.redditposts.repositories.LocalDataRepository
import com.pablojmuratore.redditposts.repositories.PostsRepository
import com.pablojmuratore.redditposts.repositories.RemoteDataRepository
import com.pablojmuratore.redditposts.room.AppDatabase
import com.pablojmuratore.redditposts.room.RedditPostDbEntityMapper
import kotlinx.coroutines.launch

class PostDetailViewModel : ViewModel() {
    private var _redditPost = MutableLiveData<RedditPost?>()
    val redditPost: LiveData<RedditPost?> get() = _redditPost
    private val database = AppDatabase.getInstance()
    private val remoteDataRepository = RemoteDataRepository(RedditPostNetworkEntityMapper())
    private val localDataRepository = LocalDataRepository(database, RedditPostDbEntityMapper())
    private val postsRepository = PostsRepository(remoteDataRepository, localDataRepository)

    init {
        _redditPost.value = null
    }

    fun loadRedditPost(redditPostId: String) {
        if (_redditPost.value == null || redditPostId != _redditPost.value!!.id) {
            viewModelScope.launch {
                val redditPost = postsRepository.getRedditPostById(redditPostId)
                _redditPost.value = redditPost
            }
        }
    }

    fun clearRedditPost() {
        _redditPost.value = null
    }

    fun markPostRead(redditPostId: String) {
        viewModelScope.launch {
            localDataRepository.markPostRead(redditPostId)
        }
    }
}