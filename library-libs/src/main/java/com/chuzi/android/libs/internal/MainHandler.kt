package com.chuzi.android.libs.internal

import android.os.Handler
import android.os.Looper

/**
 * desc
 * author: 主线程工具类
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
object MainHandler {

    private val mHandler by lazy { Handler(Looper.getMainLooper()) }

    /**
     * 在主线程执行runnable
     *
     * @param runnable 主线程待执行的任务
     */
    fun post(runnable: Runnable) {
        if (Looper.getMainLooper().thread === Thread.currentThread()) {
            // 当前线程已经在主线程，立即执行
            runnable.run()
        } else {
            // 当前线程已经在后台线程，切换到主线程后执行
            mHandler.post(runnable)
        }
    }

    fun post(closure: () -> Unit) {
        post(Runnable { closure.invoke() })
    }

    /**
     * 延迟delayMillis毫秒后，在主线程执行runnable
     *
     * @param runnable    主线程待执行的任务
     * @param delayMillis 延时X毫秒后执行任务
     */
    fun postDelayed(runnable: Runnable, delayMillis: Long) {
        mHandler.postDelayed(runnable, delayMillis)
    }

    fun postDelayed(delayMillis: Long = 150, closure: () -> Unit) {
        postDelayed(
            Runnable { closure.invoke() },
            delayMillis
        )
    }

    /**
     * 删除待执行的runnable
     *
     * @param runnable 待执行任务
     */
    fun removeCallbacks(runnable: Runnable) {
        mHandler.removeCallbacks(runnable)
    }


}