package com.pablojmuratore.redditposts.adapters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pablojmuratore.redditposts.model.RedditPost

class RedditPostsListAdapter(private val redditPostEventsListener: IRedditPostEventsListener? = null) : PagedListAdapter<RedditPost, RecyclerView.ViewHolder>(RedditPostDiffUtilCallback()), RedditPostListItem.IRedditPostListItemEventsListener {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RedditPostListItem.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val redditPost = getItem(position)
        RedditPostListItem.bindViewHolder(holder as RedditPostListItem.RedditPostViewHolder, redditPost, this)
    }

    override fun getItemViewType(position: Int): Int {
        return ListItemTypes.LIST_ITEM_TYPE_REDDIT_POST
    }

    interface IRedditPostEventsListener {
        fun onRedditPostClicked(redditPost: RedditPost)
        fun onRedditPostDismissed(redditPost: RedditPost)
    }

    override fun onRedditPostListItemClicked(redditPostId: String) {
        val post = getPostById(redditPostId)

        if (post != null) {
            redditPostEventsListener?.onRedditPostClicked(post)
        }
    }

    override fun onRedditPostDismissClicked(redditPostId: String) {
        val post = getPostById(redditPostId)

        if (post != null) {
            redditPostEventsListener?.onRedditPostDismissed(post)
        }
    }

    private fun getPostById(redditPostId: String): RedditPost? {
        for (pos in 0..itemCount) {
            if ((getItem(pos) as RedditPost).id == redditPostId) {
                return getItem(pos) as RedditPost
            }
        }

        return null
    }
}
