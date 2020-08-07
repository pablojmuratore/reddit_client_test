package com.pablojmuratore.redditposts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.pablojmuratore.redditposts.R
import com.pablojmuratore.redditposts.model.RedditPost
import com.pablojmuratore.redditposts.util.GlideApp
import java.util.*

class RedditPostListItem {
    companion object {
        private const val layoutId = R.layout.list_item_reddit_post

        fun createViewHolder(@NonNull parent: ViewGroup): RedditPostViewHolder {
            return RedditPostViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
        }

        fun bindViewHolder(holder: RedditPostViewHolder, redditPost: RedditPost?, listItemEventsListener: IRedditPostListItemEventsListener) {
            if (redditPost != null) {
                holder.container.tag = redditPost.id

                holder.dismissing = false

                val readColor = if (!redditPost.read) {
                    ContextCompat.getColor(holder.container.context, R.color.text)
                } else {
                    ContextCompat.getColor(holder.container.context, R.color.text_read)
                }

                holder.readStatus.visibility = if (!redditPost.read) View.VISIBLE else View.INVISIBLE
                holder.author.text = redditPost.author
                holder.author.setTextColor(readColor)
                holder.postedTime.text = buildPostedTime(holder.postedTime.context.getString(R.string.hours_ago), redditPost.createdUtc)
                holder.postedTime.setTextColor(readColor)
                if (!redditPost.isTextPost) {
                    holder.image.visibility = View.VISIBLE

                    GlideApp.with(holder.image.context)
                        .load(redditPost.thumbnail)
                        .error(R.drawable.ic_baseline_broken_image_24)
                        .into(holder.image)
                } else {
                    holder.image.visibility = View.GONE
                }
                holder.title.text = redditPost.title
                holder.title.setTextColor(readColor)
                holder.numComments.text = String.format(holder.numComments.context.getString(R.string.num_comments), redditPost.numComments)

                holder.container.setOnClickListener {
                    listItemEventsListener.onRedditPostListItemClicked(it.tag.toString())
                }

                holder.dismissButton.setOnClickListener {
                    holder.dismissButton.setOnClickListener(null)

                    holder.dismissing = true

                    listItemEventsListener.onRedditPostDismissClicked(redditPost.id)
                }

                holder.viewPostButton.setColorFilter(readColor, android.graphics.PorterDuff.Mode.SRC_IN);
            }
        }

        private fun buildPostedTime(templateString: String, created: Date): String {
            val hours = ((System.currentTimeMillis() / 1000 - created.time) / 3600).toInt()
            return String.format(templateString, hours)
        }
    }

    class RedditPostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var container = view.findViewById<ConstraintLayout>(R.id.reddit_post_container)
        var readStatus = view.findViewById<ImageView>(R.id.read_status)
        var author = view.findViewById<MaterialTextView>(R.id.author)
        var postedTime = view.findViewById<MaterialTextView>(R.id.posted_time)
        var image = view.findViewById<ImageView>(R.id.image)
        var title = view.findViewById<MaterialTextView>(R.id.title)
        var viewPostButton = view.findViewById<ImageView>(R.id.view_post_button)
        var dismissButton = view.findViewById<MaterialTextView>(R.id.dismiss_button)
        var numComments = view.findViewById<MaterialTextView>(R.id.num_comments)
        var dismissing: Boolean = false
    }

    interface IRedditPostListItemEventsListener {
        fun onRedditPostListItemClicked(redditPostId: String)
        fun onRedditPostDismissClicked(redditPostId: String)
    }
}