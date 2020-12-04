package com.chuzi.android.demo.main.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.chuzi.android.mvvm.base.BaseItemViewModel
import com.chuzi.android.mvvm.base.BaseViewModel
import com.chuzi.android.mvvm.ext.createCommand

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
