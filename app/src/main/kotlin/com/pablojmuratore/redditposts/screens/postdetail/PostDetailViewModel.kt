package com.pablojmuratore.redditposts.screens.postdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.repositories.LocalDataRepository
import com.pablojmuratore.redditposts.repositories.PostsRepository
import kotlinx.coroutines.launch

class PostDetailViewModel
@ViewModelInject constructor(
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