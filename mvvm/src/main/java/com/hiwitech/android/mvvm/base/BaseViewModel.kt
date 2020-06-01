package com.hiwitech.android.mvvm.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.navigation.AnimBuilder
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
abstract class BaseViewModel<TArg : BaseArg> : ViewModel(),
    LifecycleScopeProvider<BaseViewModel.ViewModelEvent>,
    LifecycleViewModel, IBaseView<TArg>, IBaseCommon {

    private val lifecycleEvents = BehaviorSubject.createDefault(ViewModelEvent.CREATED)

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
        animBuilder: AnimBuilder?,
        destinationId: Int?,
        popUpTo: Int?,
        inclusive: Boolean?,
        singleTop: Boolean?,
        extras: Navigator.Extras?
    ) {
        val baseArg = arg ?: ArgDefault()
        animBuilder?.let {
            baseArg.enterAnim = it.enter
            baseArg.exitAnim = it.exit
            baseArg.popEnterAnim = it.popEnter
            baseArg.popExitAnim = it.popEnter
        }
        uc.onStartEvent.value = Payload.Start(
            actionId,
            baseArg,
            animBuilder,
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
        animBuilder: AnimBuilder?,
        options: Bundle?,
        isPop: Boolean?,
        closure: (Intent.() -> Unit)?
    ) {
        val baseArg = arg ?: ArgDefault()
        animBuilder?.let {
            baseArg.enterAnim = it.enter
            baseArg.exitAnim = it.exit
            baseArg.popEnterAnim = it.popEnter
            baseArg.popExitAnim = it.popEnter
        }
        uc.onStartActivityEvent.value = Payload.StartActivity(
            clazz,
            baseArg,
            animBuilder,
            options,
            isPop,
            closure
        )
    }

    override fun finish() {
        uc.onFinishEvent.call()
    }

    override fun onCleared() {
        lifecycleEvents.onNext(ViewModelEvent.CLEARED)
        super.onCleared()
    }


    override fun lifecycle(): Observable<ViewModelEvent> {
        return lifecycleEvents.hide()
    }

    override fun correspondingEvents(): CorrespondingEventsFunction<ViewModelEvent> {
        return CORRESPONDING_EVENTS
    }

    override fun peekLifecycle(): ViewModelEvent? {
        return lifecycleEvents.value
    }

    companion object {
        private val CORRESPONDING_EVENTS = CorrespondingEventsFunction<ViewModelEvent> { event ->
            when (event) {
                ViewModelEvent.CREATED -> ViewModelEvent.CLEARED
                else -> throw LifecycleEndedException(
                    "Cannot bind to ViewModel lifecycle after onCleared."
                )
            }
        }
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

    enum class ViewModelEvent {
        CREATED, CLEARED
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