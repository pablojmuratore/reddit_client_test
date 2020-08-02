package com.pablojmuratore.redditposts.repositories

import com.pablojmuratore.redditposts.model.RedditPost

class PostsRepository(private val remoteDataRepository: IRemoteDataRepository) {
    suspend fun getTopPosts(): List<RedditPost> {
        return remoteDataRepository.getTopPosts()
    }
}