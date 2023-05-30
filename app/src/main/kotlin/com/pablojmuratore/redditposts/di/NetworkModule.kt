package com.pablojmuratore.redditposts.di

import com.pablojmuratore.redditposts.network.RedditPostNetworkEntityMapper
import com.pablojmuratore.redditposts.repositories.IRemoteDataRepository
import com.pablojmuratore.redditposts.repositories.RemoteDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideRemoteDataRepositoryInterface(
        remoteDataEntityMapper: RedditPostNetworkEntityMapper
    ): IRemoteDataRepository {
        return RemoteDataRepository(remoteDataEntityMapper)
    }
}