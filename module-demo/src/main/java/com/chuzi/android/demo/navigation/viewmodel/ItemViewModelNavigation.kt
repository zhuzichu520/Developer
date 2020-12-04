package com.chuzi.android.demo.navigation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.chuzi.android.mvvm.base.BaseItemViewModel
import com.chuzi.android.mvvm.base.BaseViewModel
import com.chuzi.android.mvvm.ext.createCommand

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
