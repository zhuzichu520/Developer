package com.hiwitech.android.shared

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.hiwitech.android.mvvm.Mvvm
import com.hiwitech.android.widget.crash.CrashConfig
import com.hiwitech.android.shared.global.AppGlobal
import com.hiwitech.android.shared.skin.SkinManager
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager
import jonathanfinerty.once.Once
import okhttp3.OkHttpClient
import rxhttp.RxHttp
import rxhttp.wrapper.ssl.SSLSocketFactoryImpl
import rxhttp.wrapper.ssl.X509TrustManagerImpl
import java.util.concurrent.TimeUnit

/**
 * desc
 * author: 朱子楚
 * time: 2020/7/10 1:46 PM
 * since: v 1.0.0
 */
class ApplicationSimple : Application() {

    override fun onCreate() {
        super.onCreate()
        AppGlobal.init(this)
        Once.initialise(this)
        // 或者，调试模式下会有日志输出
        RxHttp.init(getDefaultOkHttpClient(), BuildConfig.DEBUG)
        CrashConfig.Builder.create().apply()
        Mvvm.loadingLayoutId = R.layout.widget_layout_loading
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        QMUISwipeBackActivityManager.init(this)
        ARouter.init(this)
        SkinManager.install(this)
    }

    private fun getDefaultOkHttpClient(): OkHttpClient {
        val trustAllCert = X509TrustManagerImpl()
        val sslSocketFactory = SSLSocketFactoryImpl(trustAllCert)
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .sslSocketFactory(sslSocketFactory, trustAllCert) // 添加信任证书
            .build()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}
