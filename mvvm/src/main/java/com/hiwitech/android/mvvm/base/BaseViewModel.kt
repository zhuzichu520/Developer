package com.hiwitech.android.mvvm.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.hiwitech.android.mvvm.event.SingleLiveEvent
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.FlowableSubscribeProxy
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * desc ViewModel的基类
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
abstract class BaseViewModel<TArg : BaseArg> : ViewModel(), LifecycleViewModel, IBaseView<TArg>,
    IBaseCommon {

    internal val onNavigateEvent: SingleLiveEvent<Payload.Navigate> = SingleLiveEvent()
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

    override fun navigate(route: String, arg: BaseArg?) {
        onNavigateEvent.value = Payload.Navigate(
            route,
            arg ?: ArgDefault()
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