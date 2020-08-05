package com.pablojmuratore.redditposts.util

import android.app.DownloadManager
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import java.io.File

class ImageDownloader {
    var lastStatus = -100

    fun downloadImage(imageUrl: String, downloadManager: DownloadManager) {
        val directory = File(Environment.DIRECTORY_PICTURES)

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val downloadUri = Uri.parse(imageUrl)

        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(imageUrl.substring(imageUrl.lastIndexOf("/") + 1))
                .setDescription("")
                .setDestinationInExternalPublicDir(
                    directory.toString(),
                    imageUrl.substring(imageUrl.lastIndexOf("/") + 1)
                )
        }

        val downloadId = downloadManager.enqueue(request)
        val query = DownloadManager.Query().setFilterById(downloadId)

        Thread(Runnable {
            var downloading = true

            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                if (status != lastStatus) {
                    // implement callback
                    lastStatus = status
                }
                cursor.close()
            }
        }).start()
    }

    interface IImageDownloaderEvents {
        fun onEvent(event: Int)
    }

}