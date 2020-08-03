package com.pablojmuratore.redditposts.room

import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.util.IEntityMapper
import java.util.*

class RedditPostDbEntityMapper : IEntityMapper<RedditPostDbEntity, RedditPost> {
    override fun mapFromEntity(entity: RedditPostDbEntity): RedditPost {
        return RedditPost(
            entity.author,
            Date(entity.createdUtc),
            entity.thumbnail,
            entity.title,
            entity.numComments,
            entity.read
        )
    }

    override fun mapToEntity(domainModel: RedditPost): RedditPostDbEntity {
        return RedditPostDbEntity(
            0,
            domainModel.author,
            domainModel.createdUtc.time,
            domainModel.thumbnail,
            domainModel.title,
            domainModel.numComments,
            domainModel.read
        )
    }

    override fun mapFromEntitiesList(entities: List<RedditPostDbEntity>): List<RedditPost> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntitiesList(models: List<RedditPost>): List<RedditPostDbEntity> {
        return models.map { mapToEntity(it) }
    }
}