package com.pablojmuratore.redditposts.screens.postdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.repositories.LocalDataRepository
import com.pablojmuratore.redditposts.repositories.PostsRepository
import com.pablojmuratore.redditposts.repositories.RemoteDataRepository
import com.pablojmuratore.redditposts.room.AppDatabase
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val database: AppDatabase,
    private val remoteDataRepository: RemoteDataRepository,
    private val localDataRepository: LocalDataRepository,
    private val postsRepository: PostsRepository
) : ViewModel() {
    private var _redditPost = MutableLiveData<RedditPost?>()
    val redditPost: LiveData<RedditPost?> get() = _redditPost

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