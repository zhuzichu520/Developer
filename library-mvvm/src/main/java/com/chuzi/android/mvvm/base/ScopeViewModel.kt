package com.chuzi.android.mvvm.base

import androidx.lifecycle.ViewModel
import com.rxjava.rxlife.Scope
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

/**
 * desc
 * author: 朱子楚
 * time: 2020/8/25 3:23 PM
 * since: v 1.0.0
 */
open class ScopeViewModel : ViewModel(), Scope {

    private val disposables: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onScopeStart(disposable: Disposable) {
        addDisposable(disposable) //订阅事件时回调
    }

    override fun onScopeEnd() {
        //事件正常结束时回调
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    private fun dispose() {
        disposables.dispose()
    }

    override fun onCleared() {
        super.onCleared() //Activity/Fragment 销毁时回调
        dispose() //中断RxJava管道
    }

}