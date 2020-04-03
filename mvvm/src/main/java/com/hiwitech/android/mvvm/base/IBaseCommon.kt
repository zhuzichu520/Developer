package com.hiwitech.android.mvvm.base

import androidx.navigation.AnimBuilder

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
        singleTop: Boolean? = null
    )
}