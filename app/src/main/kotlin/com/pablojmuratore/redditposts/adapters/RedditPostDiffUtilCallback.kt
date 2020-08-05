package com.pablojmuratore.redditposts.adapters

import androidx.recyclerview.widget.DiffUtil
import com.pablojmuratore.redditposts.model.RedditPost
import java.util.*

class RedditPostDiffUtilCallback : DiffUtil.ItemCallback<RedditPost>() {
    override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
        return oldItem.id == newItem.id
                && oldItem.author == newItem.author
                && oldItem.createdUtc == newItem.createdUtc
                && oldItem.thumbnail == newItem.thumbnail
                && oldItem.title == newItem.title
                && oldItem.numComments == newItem.numComments
                && oldItem.isTextPost == newItem.isTextPost
                && oldItem.read == newItem.read
                && oldItem.imageUrl == newItem.imageUrl
    }
}