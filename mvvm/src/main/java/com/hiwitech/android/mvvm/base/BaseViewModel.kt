package com.hiwitech.android.mvvm.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigator
import com.hiwitech.android.mvvm.event.SingleLiveEvent
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.FlowableSubscribeProxy
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleEndedException
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

/**
 * desc ViewModel的基类
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
abstract class BaseViewModel<TArg : BaseArg> : ViewModel(), LifecycleViewModel, IBaseView<TArg>,
    IBaseCommon {

    internal val onStartEvent: SingleLiveEvent<Payload.Start> = SingleLiveEvent()
    internal val onStartActivityEvent: SingleLiveEvent<Payload.StartActivity> = SingleLiveEvent()
    val onBackPressedEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    val onShowLoadingEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    val onHideLoadingEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    val onFinishEvent: SingleLiveEvent<Unit> = SingleLiveEvent()

    lateinit var lifecycleOwner: LifecycleOwner

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
        onBackPressedEvent.call()
    }

    override fun showLoading() {
        onShowLoadingEvent.call()
    }

    override fun hideLoading() {
        onHideLoadingEvent.call()
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
        onStartEvent.value = Payload.Start(
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
        onStartActivityEvent.value = Payload.StartActivity(
            clazz,
            arg ?: ArgDefault(),
            options,
            isPop,
            context,
            closure
        )
    }

    override fun finish() {
        onFinishEvent.call()
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

    fun <T> Single<T>.autoDispose(event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY): SingleSubscribeProxy<T> =
        this.`as`(
            AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(lifecycleOwner, event)
            )
        )

    fun <T> Flowable<T>.autoDispose(event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY): FlowableSubscribeProxy<T> =
        this.`as`(
            AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(lifecycleOwner, event)
            )
        )

    fun <T> Observable<T>.autoDispose(event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY): ObservableSubscribeProxy<T> =
        this.`as`(
            AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(lifecycleOwner, event)
            )
        )

}