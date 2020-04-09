package com.hiwitech.android.mvvm.base

import androidx.navigation.AnimBuilder

/**
 * desc 页面跳转是的参数
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
internal sealed class Payload {

    internal data class Start(
        val actionId: Int,
        val arg: BaseArg,
        val animBuilder: AnimBuilder?,
        val destinationId: Int?,
        val popUpTo: Int?,
        val inclusive: Boolean?,
        val singleTop: Boolean?
    )

}