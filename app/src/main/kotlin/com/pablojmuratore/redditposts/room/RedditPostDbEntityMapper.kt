package com.pablojmuratore.redditposts.room

import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.util.IEntityMapper
import java.util.*

class RedditPostDbEntityMapper : IEntityMapper<RedditPostDbEntity, RedditPost> {
    override fun mapFromEntity(entity: RedditPostDbEntity): RedditPost {
        return RedditPost(
            entity.id,
            entity.author,
            Date(entity.createdUtc),
            entity.thumbnail,
            entity.title,
            entity.numComments,
            entity.isTextPost,
            entity.read,
            entity.imageUrl
        )
    }

    override fun mapToEntity(domainModel: RedditPost): RedditPostDbEntity {
        return RedditPostDbEntity(
            domainModel.id,
            domainModel.author,
            domainModel.createdUtc.time,
            domainModel.thumbnail,
            domainModel.title,
            domainModel.numComments,
            domainModel.isTextPost,
            domainModel.read,
            domainModel.imageUrl
        )
    }

    override fun mapFromEntitiesList(entities: List<RedditPostDbEntity>): List<RedditPost> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntitiesList(models: List<RedditPost>): List<RedditPostDbEntity> {
        return models.map { mapToEntity(it) }
    }
}