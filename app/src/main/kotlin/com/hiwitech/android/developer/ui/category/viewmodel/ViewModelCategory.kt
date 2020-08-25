package com.hiwitech.android.developer.ui.category.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hiwitech.android.shared.base.ViewModelBase
import com.hiwitech.android.libs.tool.byteCountToDisplaySizeTwo
import com.hiwitech.android.mvvm.base.ArgDefault
import javax.inject.Inject

class ViewModelCategory @Inject constructor() : ViewModelBase<ArgDefault>() {
    val text = MutableLiveData<String>().apply {
        value = byteCountToDisplaySizeTwo(600)
    }
}
