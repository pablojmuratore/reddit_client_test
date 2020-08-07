package com.pablojmuratore.redditposts.screens.postslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.pablojmuratore.redditposts.R
import com.pablojmuratore.redditposts.RedditPostsApplication
import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.repositories.LocalDataRepository
import com.pablojmuratore.redditposts.repositories.PostsRepository
import com.pablojmuratore.redditposts.repositories.RemoteDataRepository
import com.pablojmuratore.redditposts.room.AppDatabase
import com.pablojmuratore.redditposts.util.NetworkHelper
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostsListViewModel
@Inject constructor(
    private val database: AppDatabase,
    private val remoteDataRepository: RemoteDataRepository,
    private val localDataRepository: LocalDataRepository,
    private val postsRepository: PostsRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    val postsList: LiveData<PagedList<RedditPost>>

    private var _refreshingPosts = MutableLiveData<Boolean>()
    val refreshingPosts: LiveData<Boolean> get() = _refreshingPosts

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    init {
        _message.value = ""

        val factory = postsRepository.getDataSourceFactoryForPosts()
        val pagedListBuilder: LivePagedListBuilder<Int, RedditPost> = LivePagedListBuilder<Int, RedditPost>(factory, 10)
        postsList = pagedListBuilder.build()

        _refreshingPosts.value = false

        viewModelScope.launch {
            if (localDataRepository.getAllPosts().isEmpty()) {
                if (networkHelper.isNetworkAvailable()) {
                    postsRepository.getTopPosts(true)
                } else {
                    _message.value = RedditPostsApplication.getContext().resources.getString(R.string.no_network_message)
                }
            }
        }
    }

    fun loadDataFromNetwork() {
        if (networkHelper.isNetworkAvailable()) {
            _refreshingPosts.value = true

            viewModelScope.launch {
                postsRepository.getTopPosts(true)
                _refreshingPosts.value = false
            }
        } else {
            _refreshingPosts.value = false
            _message.value = RedditPostsApplication.getContext().resources.getString(R.string.no_network_message)
        }
    }

    fun dismissPost(redditPost: RedditPost) {
        viewModelScope.launch {
            localDataRepository.deletePost(redditPost.id)
        }
    }

    fun dismissAllPosts() {
        viewModelScope.launch {
            localDataRepository.clearPosts()
        }
    }

    fun clearMessage() {
        _message.value = ""
    }

}