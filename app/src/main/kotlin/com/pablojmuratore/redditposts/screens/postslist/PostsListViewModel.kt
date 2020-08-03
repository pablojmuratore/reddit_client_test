package com.pablojmuratore.redditposts.screens.postslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.network.RedditPostNetworkEntityMapper
import com.pablojmuratore.redditposts.repositories.LocalDataRepository
import com.pablojmuratore.redditposts.repositories.PostsRepository
import com.pablojmuratore.redditposts.repositories.RemoteDataRepository
import com.pablojmuratore.redditposts.room.AppDatabase
import com.pablojmuratore.redditposts.room.RedditPostDbEntityMapper

class PostsListViewModel : ViewModel() {
    val postsList: LiveData<PagedList<RedditPost>>

    private val database: AppDatabase by lazy { AppDatabase.getInstance() }
    private val remoteDataRepository: RemoteDataRepository by lazy { RemoteDataRepository(RedditPostNetworkEntityMapper()) }
    private val localDataRepository: LocalDataRepository by lazy { LocalDataRepository(database, RedditPostDbEntityMapper()) }
    private val postsRepository: PostsRepository by lazy { PostsRepository(remoteDataRepository, localDataRepository) }

    init {
        val factory = postsRepository.getDataSourceFactoryForPosts()
        val pagedListBuilder: LivePagedListBuilder<Int, RedditPost> = LivePagedListBuilder<Int, RedditPost>(factory, 10)
        postsList = pagedListBuilder.build()
    }

}