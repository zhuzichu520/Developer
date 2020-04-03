package com.hiwitech.android.mvvm.base

import androidx.lifecycle.ViewModel
import androidx.navigation.AnimBuilder
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleEndedException
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import com.hiwitech.android.mvvm.event.SingleLiveEvent
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseViewModel<TArg : BaseArg> : ViewModel(),
    LifecycleScopeProvider<BaseViewModel.ViewModelEvent>,
    LifecycleViewModel, IBaseView<TArg>, IBaseCommon {

    private val lifecycleEvents = BehaviorSubject.createDefault(ViewModelEvent.CREATED)
    val uc by lazy { UIChangeLiveData() }
    var isInitData = false
    var isInitLazy = false
    var isInitLazyView = false

    lateinit var arg: TArg

    override fun back() {
        uc.onBackPressedEvent.postCall()
    }

    override fun showLoading() {
        uc.onShowLoadingEvent.postCall()
    }

    override fun hideLoading() {
        uc.onHideLoadingEvent.postCall()
    }

    override fun start(
        actionId: Int,
        arg: BaseArg?,
        animBuilder: AnimBuilder?,
        destinationId: Int?,
        popUpTo: Int?,
        inclusive: Boolean?,
        singleTop: Boolean?
    ) {
        uc.onStartEvent.value = Payload.Start(
            actionId,
            arg ?: ArgDefault(),
            animBuilder,
            destinationId,
            popUpTo,
            inclusive,
            singleTop
        )
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
        internal val onStartEvent: SingleLiveEvent<Payload.Start> =
            SingleLiveEvent()
        internal val onBackPressedEvent: SingleLiveEvent<Any> = SingleLiveEvent()
        internal val onShowLoadingEvent: SingleLiveEvent<Any> = SingleLiveEvent()
        internal val onHideLoadingEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    }

    enum class ViewModelEvent {
        CREATED, CLEARED
    }

    override fun initArgs(arg: TArg) {}

    override fun initViewObservable() {}

    override fun initVariable() {}

    override fun initView() {}

    override fun initData() {}

    override fun initFirstData() {}

    override fun initLazyData() {}

    override fun initLazyView() {}

    override fun initListener() {}

}