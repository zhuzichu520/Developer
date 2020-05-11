package com.hiwitech.android.mvvm.base

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

}