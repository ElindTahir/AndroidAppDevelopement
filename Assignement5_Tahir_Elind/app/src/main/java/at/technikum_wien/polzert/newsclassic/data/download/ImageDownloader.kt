package at.technikum_wien.polzert.newsclassic.data.download

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object ImageDownloader {
    private val LOG_TAG = ImageDownloader::class.java.simpleName
    private val executor: ExecutorService = Executors.newFixedThreadPool(5)

    fun downloadImage(url: URL, callback: (URL, Bitmap?) -> Unit) {
        executor.execute {
            var urlConnection: HttpURLConnection? = null
            try {
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 5000
                val statusCode = urlConnection.responseCode
                if (statusCode != 200) {
                    Log.e(LOG_TAG, "Error downloading image from $url. Response code: $statusCode")
                    callback(url, null)
                } else {
                    val inputStream = urlConnection.inputStream
                    if (inputStream == null) {
                        Log.e(LOG_TAG, "Error downloading image from $url")
                        callback(url, null)
                    } else {
                        // Define the options for BitmapFactory
                        val options = BitmapFactory.Options().apply {
                            inSampleSize = 4  // subsample the image by a factor of 4
                        }

                        val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                        callback(url, bitmap)
                    }
                }
            } catch (ex: IOException) {
                Log.e(LOG_TAG, "Error downloading image from $url", ex)
                callback(url, null)
            } finally {
                urlConnection?.disconnect()
            }
        }
    }
}
