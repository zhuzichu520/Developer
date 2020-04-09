package com.hiwitech.android.mvvm.base

/**
 * desc 页面初始化回调接口
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
interface IBaseView<TArg : BaseArg> {

    fun initArgs(arg: TArg)

    fun initViewObservable()

    fun initVariable()

    fun initView()

    fun initListener()

    fun initData()

    fun initFirstData()

    fun initLazyData()

    fun initLazyView()

}