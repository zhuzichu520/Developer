package com.hiwitech.android.mvvm.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.navigation.AnimBuilder
import androidx.navigation.Navigator

/**
 * desc RecyclerView中的Item ViewModel
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
open class BaseItemViewModel(
    private val viewModel: BaseViewModel<*>
) : ViewModel(), IBaseCommon {

    override fun back() {
        viewModel.back()
    }

    override fun showLoading() {
        viewModel.showLoading()
    }

    override fun hideLoading() {
        viewModel.hideLoading()
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
        viewModel.start(
            actionId,
            arg,
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
        closure: (Intent.() -> Unit)?
    ) {
        viewModel.startActivity(clazz, arg, animBuilder,options, closure)
    }

    override fun finish() {
        viewModel.finish()
    }

}