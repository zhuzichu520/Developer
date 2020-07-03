package com.hiwitech.android.mvvm.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigator
import com.hiwitech.android.mvvm.event.SingleLiveEvent
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleEndedException
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * desc ViewModel的基类
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
abstract class BaseViewModel<TArg : BaseArg> : ViewModel(), LifecycleViewModel, IBaseView<TArg>,
    IBaseCommon {


    /**
     * 初始化UI事件
     */
    val uc by lazy { UIChangeLiveData() }

    /**
     * 是否初始化数据
     */
    var isInitData = false

    /**
     * 是否懒加载初始化数据
     */
    var isInitLazy = false

    /**
     * 页面参数
     */
    lateinit var arg: TArg

    override fun back() {
        uc.onBackPressedEvent.call()
    }

    override fun showLoading() {
        uc.onShowLoadingEvent.call()
    }

    override fun hideLoading() {
        uc.onHideLoadingEvent.call()
    }

    override fun start(
        actionId: Int,
        arg: BaseArg?,
        destinationId: Int?,
        popUpTo: Int?,
        inclusive: Boolean?,
        singleTop: Boolean?,
        extras: Navigator.Extras?
    ) {
        uc.onStartEvent.value = Payload.Start(
            actionId,
            arg ?: ArgDefault(),
            destinationId,
            popUpTo,
            inclusive,
            singleTop,
            extras
        )
    }

    override fun startActivity(
        clazz: Class<out Activity>,
        arg: BaseArg?,
        options: Bundle?,
        isPop: Boolean?,
        context: Context?,
        closure: (Intent.() -> Unit)?
    ) {
        uc.onStartActivityEvent.value = Payload.StartActivity(
            clazz,
            arg ?: ArgDefault(),
            options,
            isPop,
            context,
            closure
        )
    }

    override fun finish() {
        uc.onFinishEvent.call()
    }

    inner class UIChangeLiveData {
        internal val onStartEvent: SingleLiveEvent<Payload.Start> = SingleLiveEvent()
        internal val onStartActivityEvent: SingleLiveEvent<Payload.StartActivity> =
            SingleLiveEvent()
        internal val onBackPressedEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
        internal val onShowLoadingEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
        internal val onHideLoadingEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
        internal val onFinishEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    }

    override fun initArgs(arg: TArg) {}

    override fun initViewObservable() {}

    override fun initVariable() {}

    override fun initView() {}

    override fun initData() {}

    override fun initOneData() {}

    override fun initLazyData() {}

    override fun initListener() {}

    override fun initOneObservable() {}

}