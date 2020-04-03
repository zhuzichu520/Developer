package com.hiwitech.android.shared.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.hiwitech.android.libs.internal.MainHandler
import com.hiwitech.android.libs.tool.toLong
import com.hiwitech.android.shared.global.CacheGlobal
import okhttp3.*
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.Okio
import java.io.IOException
import java.io.InputStream
import java.util.*

@GlideModule
class GlideModel : AppGlideModule() {

    companion object {

        private const val M = 1024 * 1024L
        private const val MAX_DISK_CACHE_SIZE = 256 * M

        fun forget(url: String) {
            DispatchingProgressListener.forget(url)
        }

        fun expect(url: String, listener: UiOnProgressListener) {
            DispatchingProgressListener.expect(url, listener)
        }

    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDiskCache(
            DiskLruCacheFactory(
                CacheGlobal.getGlideCacheDir(),
                MAX_DISK_CACHE_SIZE
            )
        )
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor {
                val request: Request = it.request()
                val response: Response = it.proceed(request)
                val body = response.body()
                if (body != null) {
                    val listener: ResponseProgressListener = DispatchingProgressListener()
                    response.newBuilder()
                        .body(
                            OkHttpProgressResponseBody(
                                request.url(),
                                body,
                                listener
                            )
                        )
                        .build()
                } else {
                    response
                }
            }.build()

        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(client)
        )
    }

    private class OkHttpProgressResponseBody(
        val url: HttpUrl,
        val responseBody: ResponseBody,
        val progressListener: ResponseProgressListener
    ) : ResponseBody() {

        override fun contentLength(): Long {
            return responseBody.contentLength()
        }

        override fun contentType(): MediaType? {
            return responseBody.contentType()
        }

        override fun source(): BufferedSource {
            return Okio.buffer(object : ForwardingSource(responseBody.source()) {
                var totalBytesRead = 0L
                @Throws(IOException::class)
                override fun read(sink: Buffer, byteCount: Long): Long {
                    val bytesRead = super.read(sink, byteCount)
                    val fullLength = responseBody.contentLength()
                    if (bytesRead == -1L) {
                        totalBytesRead = fullLength
                    } else {
                        totalBytesRead += bytesRead
                    }
                    progressListener.update(url, totalBytesRead, fullLength)
                    return bytesRead
                }
            })
        }

    }

    private class DispatchingProgressListener : ResponseProgressListener {

        companion object {

            private val LISTENERS: HashMap<String, UiOnProgressListener> =
                HashMap()

            private val PROGRESSES: HashMap<String, Long> =
                HashMap()

            fun forget(url: String) {
                LISTENERS.remove(url)
                PROGRESSES.remove(url)
            }

            fun expect(url: String, listener: UiOnProgressListener) {
                LISTENERS[url] = listener
            }
        }

        override fun update(url: HttpUrl, bytesRead: Long, contentLength: Long) {
            val key = url.toString()
            val listener = LISTENERS[key] ?: return
            if (contentLength <= bytesRead) {
                forget(key)
            }
            if (needsDispatch(key, bytesRead, contentLength, listener.getGranualityPercentage())) {
                MainHandler.post { listener.onProgress(bytesRead, contentLength) }
            }
        }

        private fun needsDispatch(
            key: String,
            current: Long,
            total: Long,
            granularity: Float
        ): Boolean {
            if (granularity == 0f || current == 0L || total == current) {
                return true
            }
            val percent = 100f * current / total
            val currentProgress = toLong((percent / granularity))
            val lastProgress: Long? = PROGRESSES[key]
            return if (lastProgress == null || currentProgress != lastProgress) {
                PROGRESSES[key] = currentProgress
                true
            } else {
                false
            }
        }

    }

    interface UiOnProgressListener {
        fun onProgress(bytesRead: Long, expectedLength: Long)
        fun getGranualityPercentage(): Float
    }

    private interface ResponseProgressListener {
        fun update(
            url: HttpUrl,
            bytesRead: Long,
            contentLength: Long
        )
    }
}