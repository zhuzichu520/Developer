package com.hiwitech.android.mvvm.base

import androidx.lifecycle.ViewModel

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

    override fun finish() {
        viewModel.finish()
    }

    override fun toast(text: String) {
        viewModel.toast(text)
    }

    override fun toast(textId: Int) {
        viewModel.toast(textId)
    }

    override fun navigate(route: String, arg: BaseArg?) {
        viewModel.navigate(route, arg)
    }

}