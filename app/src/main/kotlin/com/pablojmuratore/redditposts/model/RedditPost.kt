package com.pablojmuratore.redditposts.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class RedditPost(
    val id: String,
    val author: String,
    val createdUtc: Date,
    val thumbnail: String,
    val title: String,
    val numComments: Long,
    val isTextPost: Boolean,
    var read: Boolean = false
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString() ?: "",
        source.readString() ?: "",
        source.readSerializable() as Date,
        source.readString() ?: "",
        source.readString() ?: "",
        source.readLong(),
        1 == source.readInt(),
        1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(author)
        writeSerializable(createdUtc)
        writeString(thumbnail)
        writeString(title)
        writeLong(numComments)
        writeInt((if (isTextPost) 1 else 0))
        writeInt((if (read) 1 else 0))
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RedditPost> = object : Parcelable.Creator<RedditPost> {
            override fun createFromParcel(source: Parcel): RedditPost = RedditPost(source)
            override fun newArray(size: Int): Array<RedditPost?> = arrayOfNulls(size)
        }
    }
}
