package com.hiwitech.android.demo.main.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.hiwitech.android.mvvm.base.BaseItemViewModel
import com.hiwitech.android.mvvm.base.BaseViewModel
import com.hiwitech.android.mvvm.ext.createCommand

class ItemViewModelDemo(
        viewModel: BaseViewModel<*>,
        type: Int,
        @StringRes stringId: Int,
        closure: Int.() -> Unit
) : BaseItemViewModel(viewModel) {

    val title = MutableLiveData(stringId)

    val onClickCommand = createCommand {
        closure.invoke(type)
    }

}
