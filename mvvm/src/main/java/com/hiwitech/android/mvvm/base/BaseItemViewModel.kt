package com.hiwitech.android.mvvm.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
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
        navController: NavController?,
        destinationId: Int?,
        popUpTo: Int?,
        inclusive: Boolean?,
        singleTop: Boolean?,
        extras: Navigator.Extras?
    ) {
        viewModel.start(
            actionId,
            arg,
            navController,
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
        options: Bundle?,
        isPop: Boolean?,
        context: Context?,
        closure: (Intent.() -> Unit)?
    ) {
        viewModel.startActivity(clazz, arg, options, isPop, context, closure)
    }

    override fun finish() {
        viewModel.finish()
    }

}