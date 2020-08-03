package com.pablojmuratore.redditposts.repositories

import com.pablojmuratore.redditposts.model.RedditPost

class PostsRepository(private val remoteDataRepository: IRemoteDataRepository, private val localDataRepository: ILocalDataRepository) {
    suspend fun getTopPosts(skipCache: Boolean = false): List<RedditPost> {
        return if (skipCache) {
            localDataRepository.clearPosts()

            val remotePosts = remoteDataRepository.getTopPosts()
            localDataRepository.savePosts(remotePosts)

            localDataRepository.getAllPosts()
        } else {
            var localPosts = localDataRepository.getAllPosts()

            return if (localPosts.isNotEmpty()) {
                localPosts
            } else {
                val remotePosts = remoteDataRepository.getTopPosts()
                localDataRepository.savePosts(remotePosts)

                localPosts = localDataRepository.getAllPosts()

                localPosts
            }
        }
    }

}
