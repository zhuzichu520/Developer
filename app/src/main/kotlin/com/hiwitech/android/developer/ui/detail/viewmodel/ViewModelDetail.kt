package com.hiwitech.android.developer.ui.detail.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hiwitech.android.developer.base.ViewModelBase
import com.hiwitech.android.developer.ui.detail.arg.ArgText
import com.hiwitech.android.mvvm.base.BaseViewModel
import com.hiwitech.android.shared.ext.createCommand
import javax.inject.Inject

class ViewModelDetail @Inject constructor() : ViewModelBase<ArgText>() {

    val content = MutableLiveData<String>()

    val onClickHomeEvent = createCommand {
    }

    override fun initArgs(arg: ArgText) {
        content.value = arg.content
    }
}
