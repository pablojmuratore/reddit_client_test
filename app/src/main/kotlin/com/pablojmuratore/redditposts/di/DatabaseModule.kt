package com.pablojmuratore.redditposts.di

import com.pablojmuratore.redditposts.repositories.ILocalDataRepository
import com.pablojmuratore.redditposts.repositories.LocalDataRepository
import com.pablojmuratore.redditposts.room.AppDatabase
import com.pablojmuratore.redditposts.room.RedditPostDbEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(): AppDatabase {
        return AppDatabase.getInstance()
    }

    @Singleton
    @Provides
    fun provideLocalDataRepositoryInterface(
        database: AppDatabase,
        redditPostDbEntityMapper: RedditPostDbEntityMapper
    ): ILocalDataRepository {
        return LocalDataRepository(database, redditPostDbEntityMapper)
    }

}