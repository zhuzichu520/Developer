package com.hiwitech.android.mvvm.base

import androidx.lifecycle.LifecycleOwner
import com.hiwitech.android.mvvm.event.SingleLiveEvent

/**
 * desc ViewModel的基类
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
abstract class BaseViewModel<TArg : BaseArg> : ScopeViewModel(), LifecycleViewModel,
    IBaseView<TArg>,
    IBaseCommon {

    internal val onNavigateEvent: SingleLiveEvent<Payload.Navigate> = SingleLiveEvent()
    val onBackPressedEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    val onShowLoadingEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    val onHideLoadingEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    val onFinishEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    val onToastStringEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val onToastIntEvent: SingleLiveEvent<Int> = SingleLiveEvent()

    lateinit var lifecycleOwner: LifecycleOwner

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

    override fun toast(text: String) {
        onToastStringEvent.value = text
    }

    override fun toast(textId: Int) {
        onToastIntEvent.value = textId
    }

    override fun navigate(route: String, arg: BaseArg?, isPop: Boolean?) {
        onNavigateEvent.value = Payload.Navigate(
            route,
            arg ?: ArgDefault(),
            isPop ?: false
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

    override fun initLazyData() {}

    override fun initListener() {}

}