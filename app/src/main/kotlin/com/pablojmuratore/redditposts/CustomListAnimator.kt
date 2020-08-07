package com.pablojmuratore.redditposts

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.pablojmuratore.redditposts.adapters.RedditPostListItem

class CustomListAnimator : DefaultItemAnimator() {
    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        if (holder is RedditPostListItem.RedditPostViewHolder && holder.dismissing) {
            holder.let {
                val animation = AnimationUtils.loadAnimation(it.itemView.context, R.anim.list_item_remove_animation)
                it.itemView?.animation = animation.also {
                    dispatchAnimationsFinished()
                }
            }
        } else {
            dispatchAnimationsFinished()
        }

        return super.animateRemove(holder)
    }

    override fun animatePersistence(viewHolder: RecyclerView.ViewHolder, preInfo: RecyclerView.ItemAnimator.ItemHolderInfo, postInfo: RecyclerView.ItemAnimator.ItemHolderInfo): Boolean {
        dispatchAnimationFinished(viewHolder)
        return false
    }

    override fun getMoveDuration(): Long {
        return 100
    }

    override fun getRemoveDuration(): Long {
        return 200
    }

    override fun getAddDuration(): Long {
        return 50
    }

}