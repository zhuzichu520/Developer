package com.hiwitech.android.mvvm.base

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