package com.hiwitech.android.mvvm.base

import androidx.navigation.AnimBuilder
import androidx.navigation.Navigator

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

    fun start(
        actionId: Int,
        arg: BaseArg? = null,
        animBuilder: AnimBuilder? = null,
        destinationId: Int? = null,
        popUpTo: Int? = null,
        inclusive: Boolean? = null,
        singleTop: Boolean? = null,
        extras: Navigator.Extras? = null
    )
}