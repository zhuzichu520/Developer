package com.chuzi.android.shared.global

import com.chuzi.android.libs.tool.isExternalStorageWriteable
import com.chuzi.android.shared.global.AppGlobal.context
import java.io.File

object CacheGlobal {

    const val CACHE_FRESCO_FILE_NAME = "cache_fresco"

    private const val CACHE_MMKV_FILE_NAME = "cache_mmkv"

    private const val CACHE_LOG_FILE_NAME = "cache_log"

    fun initDir() {
        getGlideCacheDir()
    }

    fun getLogCacheDir(): String {
        return getDiskCacheDir(CACHE_LOG_FILE_NAME).absolutePath
    }

    fun getMmkvCacheDir(): String {
        return getDiskCacheDir(CACHE_MMKV_FILE_NAME).absolutePath
    }

    fun getGlideCacheDir(): String {
        return getDiskCacheDir(CACHE_FRESCO_FILE_NAME).absolutePath
    }

    fun getBaseDiskCacheDir(): File {
        return if (isExternalStorageWriteable()) {
            context.externalCacheDir!!
        } else {
            context.cacheDir
        }
    }

    private fun getDiskCacheDir(last: String): File {
        val file = File(getBaseDiskCacheDir().toString(), last)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absoluteFile
    }

}