package com.hiwitech.android.developer.ui.demo.main.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.hiwitech.android.mvvm.base.BaseItemViewModel
import com.hiwitech.android.mvvm.base.BaseViewModel
import com.hiwitech.android.shared.ext.createCommand

class ItemViewModelDemo(
    viewModel: BaseViewModel<*>,
    type: Int,
    @StringRes stringId: Int,
    closure: Int.() -> Unit
) : BaseItemViewModel(viewModel) {

    val title = MutableLiveData(stringId)

    val onClickItem = createCommand {
        closure.invoke(type)
    }

}