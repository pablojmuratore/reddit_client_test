package com.pablojmuratore.redditposts.screens.postslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.network.RedditPostNetworkEntityMapper
import com.pablojmuratore.redditposts.repositories.LocalDataRepository
import com.pablojmuratore.redditposts.repositories.PostsRepository
import com.pablojmuratore.redditposts.repositories.RemoteDataRepository
import com.pablojmuratore.redditposts.room.AppDatabase
import com.pablojmuratore.redditposts.room.RedditPostDbEntityMapper
import kotlinx.coroutines.launch

class PostsListViewModel : ViewModel() {
    val postsList: LiveData<PagedList<RedditPost>>

    private var _currentPost = MutableLiveData<RedditPost?>()
    val currentPost: LiveData<RedditPost?> get() = _currentPost

    private var _refreshingPosts = MutableLiveData<Boolean>()
    val refreshingPosts: LiveData<Boolean> get() = _refreshingPosts

    private val database: AppDatabase by lazy { AppDatabase.getInstance() }
    private val remoteDataRepository: RemoteDataRepository by lazy { RemoteDataRepository(RedditPostNetworkEntityMapper()) }
    private val localDataRepository: LocalDataRepository by lazy { LocalDataRepository(database, RedditPostDbEntityMapper()) }
    private val postsRepository: PostsRepository by lazy { PostsRepository(remoteDataRepository, localDataRepository) }

    init {
        val factory = postsRepository.getDataSourceFactoryForPosts()
        val pagedListBuilder: LivePagedListBuilder<Int, RedditPost> = LivePagedListBuilder<Int, RedditPost>(factory, 10)
        postsList = pagedListBuilder.build()

        _currentPost.value = null
        _refreshingPosts.value = false

        viewModelScope.launch {
            if (localDataRepository.getAllPosts().size == 0) {
                postsRepository.getTopPosts(true)
            }
        }
    }

    fun loadDataFromNetwork() {
        _refreshingPosts.value = true
        _currentPost.value = null

        viewModelScope.launch {
            postsRepository.getTopPosts(true)
            _refreshingPosts.value = false
        }
    }

    fun showPost(redditPost: RedditPost?) {
        _currentPost.value = redditPost
    }

    fun dismissPost(redditPost: RedditPost) {
        viewModelScope.launch {
            localDataRepository.deletePost(redditPost.id)

            if (_currentPost.value != null && redditPost.id == _currentPost.value!!.id) {
                _currentPost.value = null
            }
        }
    }

    fun dismissAllPosts() {
        viewModelScope.launch {
            _currentPost.value = null
            localDataRepository.clearPosts()
        }
    }

}