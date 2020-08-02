package com.pablojmuratore.redditposts.network

import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.util.IEntityMapper

class RedditPostsNetworkEntityMapper : IEntityMapper<RedditPostNetworkEntity, RedditPost> {
    override fun mapFromEntity(entity: RedditPostNetworkEntity): RedditPost {
        return RedditPost(
            entity.data.author,
            entity.data.createdUtc,
            entity.data.thumbnail,
            entity.data.title,
            entity.data.numComments
        )
    }

    override fun mapToEntity(domainModel: RedditPost): RedditPostNetworkEntity {
        return RedditPostNetworkEntity(
            data = RedditPostDataNetworkEntity(
                domainModel.author,
                domainModel.createdUtc,
                domainModel.thumbnail,
                domainModel.title,
                domainModel.numComments
            )
        )
    }

    override fun mapFromEntitiesList(entities: List<RedditPostNetworkEntity>): List<RedditPost> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntitiesList(models: List<RedditPost>): List<RedditPostNetworkEntity> {
        return models.map { mapToEntity(it) }
    }

}