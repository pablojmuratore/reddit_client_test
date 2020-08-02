package com.pablojmuratore.redditposts.network

import com.pablojmuratore.redditposts.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.REDDIT_ENDPOINT)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(OkHttpClientCustom.getLoggingInterceptor())
    .build()

object RedditApi {
    private val retrofitService by lazy { retrofit.create(IRedditApi::class.java) }
    private const val TOP_LIMIT = 50

    suspend fun getTopPosts(): List<RedditPostNetworkEntity> {
        return retrofitService.getTopPosts(TOP_LIMIT).data.children
    }
}

interface IRedditApi {
    @GET("top.json")
    suspend fun getTopPosts(@Query("limit") limit: Int): TopPostsResponse
}