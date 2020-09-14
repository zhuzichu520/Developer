package com.hiwitech.android.shared.global

import android.app.Application
import android.content.Context
import com.hiwitech.android.libs.tool.decodeBase64
import com.hiwitech.android.libs.tool.json2Object
import com.hiwitech.android.mvvm.base.IApplication
import com.hiwitech.android.shared.BuildConfig
import com.hiwitech.android.shared.module.Modules
import com.hiwitech.android.widget.log.lumberjack.FileLoggingSetup
import com.hiwitech.android.widget.log.lumberjack.FileLoggingTree
import com.hiwitech.android.widget.log.lumberjack.L
import com.tencent.mmkv.MMKV
import timber.log.ConsoleTree

object AppGlobal {

    private lateinit var application: Application

    val context: Context by lazy {
        application.applicationContext
    }

    fun init(application: Application): AppGlobal {
        AppGlobal.application = application
        CacheGlobal.initDir()
        MMKV.initialize(CacheGlobal.getMmkvCacheDir())
        L.plant(ConsoleTree())
        L.plant(FileLoggingTree(FileLoggingSetup(context).withFolder(CacheGlobal.getLogCacheDir())))
        json2Object(decodeBase64(BuildConfig.MODULE_JSON), Modules::class.java)?.data?.forEach {
            try {
                val clz = Class.forName(it.application)
                val obj = clz.newInstance()
                if (obj is IApplication) {
                    L.tag("zzc").i { "初始化模块Application:".plus(it.application) }
                    obj.init(application)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return this
    }

}