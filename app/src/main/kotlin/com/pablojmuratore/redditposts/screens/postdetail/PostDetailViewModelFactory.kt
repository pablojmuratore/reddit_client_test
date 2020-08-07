package com.pablojmuratore.redditposts.screens.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pablojmuratore.redditposts.repositories.LocalDataRepository
import com.pablojmuratore.redditposts.repositories.PostsRepository
import com.pablojmuratore.redditposts.repositories.RemoteDataRepository
import com.pablojmuratore.redditposts.room.AppDatabase

class PostDetailViewModelFactory : ViewModelProvider.Factory {
    private val database: AppDatabase
    private val remoteDataRepository: RemoteDataRepository
    private val localDataRepository: LocalDataRepository
    private val postsRepository: PostsRepository

    constructor(
        database: AppDatabase,
        remoteDataRepository: RemoteDataRepository,
        localDataRepository: LocalDataRepository,
        postsRepository: PostsRepository
    ) {
        this.database = database
        this.remoteDataRepository = remoteDataRepository
        this.localDataRepository = localDataRepository
        this.postsRepository = postsRepository
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostDetailViewModel(database, remoteDataRepository, localDataRepository, postsRepository) as T
    }
}