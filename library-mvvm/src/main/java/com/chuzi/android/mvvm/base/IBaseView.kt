package com.chuzi.android.mvvm.base

/**
 * desc 页面初始化回调接口
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
interface IBaseView<TArg : BaseArg> {

    /**
     * 初始化页面参数
     */
    fun initArgs(arg: TArg)

    /**
     * 初始化View观察事件
     */
    fun initViewObservable()

    /**
     * DataBinding数据
     */
    fun initVariable()

    /**
     * 初始化View
     */
    fun initView()

    /**
     * 初始化事件
     */
    fun initListener()

    /**
     * 初始化数据
     */
    fun initData()

    /**
     * 懒加载数据
     */
    fun initLazyData()

}