package com.hiwitech.android.mvvm.base

import androidx.annotation.StringRes

/**
 * desc 页面通用功能接口
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
interface IBaseCommon {

    fun back()

    fun showLoading()

    fun hideLoading()

    fun navigate(route: String, arg: BaseArg? = null)

    fun finish()

    fun toast(text: String)

    fun toast(@StringRes textId: Int)

}