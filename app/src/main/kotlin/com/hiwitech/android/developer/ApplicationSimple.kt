package com.hiwitech.android.developer

import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.hiwitech.android.developer.di.DaggerAppComponent
import com.hiwitech.android.mvvm.Mvvm
import com.hiwitech.android.shared.crash.CrashConfig
import com.hiwitech.android.shared.global.AppGlobal
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import jonathanfinerty.once.Once
import okhttp3.OkHttpClient
import rxhttp.RxHttp
import rxhttp.wrapper.ssl.SSLSocketFactoryImpl
import rxhttp.wrapper.ssl.X509TrustManagerImpl
import java.util.concurrent.TimeUnit

class ApplicationSimple : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        AppGlobal.init(this)
        Once.initialise(this)
        // 或者，调试模式下会有日志输出
        RxHttp.init(getDefaultOkHttpClient(), BuildConfig.DEBUG)
        CrashConfig.Builder.create().apply()
        Mvvm.loadingLayoutId = R.layout.layout_loading
        Mvvm.setAnimBuilder(
            R.anim.no_anim,
            R.anim.no_anim,
            R.anim.no_anim,
            R.anim.no_anim
        )

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        // 尽可能早，推荐在Application中初始化
        QMUISwipeBackActivityManager.init(this)
        ARouter.init(this)
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

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
