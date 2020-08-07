package com.pablojmuratore.redditposts.screens.postslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pablojmuratore.redditposts.repositories.LocalDataRepository
import com.pablojmuratore.redditposts.repositories.PostsRepository
import com.pablojmuratore.redditposts.repositories.RemoteDataRepository
import com.pablojmuratore.redditposts.room.AppDatabase
import com.pablojmuratore.redditposts.util.NetworkHelper
import dagger.hilt.android.AndroidEntryPoint

class PostsListViewModelFactory : ViewModelProvider.Factory {
    private val database: AppDatabase
    private val remoteDataRepository: RemoteDataRepository
    private val localDataRepository: LocalDataRepository
    private val postsRepository: PostsRepository
    private val networkHelper: NetworkHelper

    constructor(
        database: AppDatabase,
        remoteDataRepository: RemoteDataRepository,
        localDataRepository: LocalDataRepository,
        postsRepository: PostsRepository,
        networkHelper: NetworkHelper
    ) {
        this.database = database
        this.remoteDataRepository = remoteDataRepository
        this.localDataRepository = localDataRepository
        this.postsRepository = postsRepository
        this.networkHelper = networkHelper
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostsListViewModel(database, remoteDataRepository, localDataRepository, postsRepository, networkHelper) as T
    }

}