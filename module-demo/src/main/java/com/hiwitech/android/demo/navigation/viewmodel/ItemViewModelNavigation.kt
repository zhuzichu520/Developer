package com.hiwitech.android.demo.navigation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.hiwitech.android.mvvm.base.BaseItemViewModel
import com.hiwitech.android.mvvm.base.BaseViewModel
import com.hiwitech.android.mvvm.ext.createCommand

class ItemViewModelNavigation(
        viewModel: BaseViewModel<*>,
        @StringRes stringId: Int,
        closure: () -> Unit
) : BaseItemViewModel(viewModel) {

    val title = MutableLiveData(stringId)

    val onClickCommand = createCommand {
        closure.invoke()
    }

}
