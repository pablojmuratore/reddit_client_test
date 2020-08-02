package com.pablojmuratore.redditposts.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OkHttpClientCustom {
    companion object {
        var okHttpClient: OkHttpClient? = null
        private const val NETWORK_TIMEOUT: Long = 3000

        fun getLoggingInterceptor(): OkHttpClient {
            if (okHttpClient == null) {
                val logging = HttpLoggingInterceptor()

                logging.level = HttpLoggingInterceptor.Level.BODY

                okHttpClient = OkHttpClient().newBuilder()
                    .connectTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                    .addInterceptor(logging)
                    .build()
            }

            return okHttpClient!!
        }
    }
}