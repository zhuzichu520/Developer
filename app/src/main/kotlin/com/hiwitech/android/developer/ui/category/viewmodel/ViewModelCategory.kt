package com.hiwitech.android.developer.ui.category.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hiwitech.android.developer.base.ViewModelBase
import com.hiwitech.android.libs.tool.byteCountToDisplaySizeTwo
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.mvvm.base.BaseViewModel
import javax.inject.Inject

class ViewModelCategory @Inject constructor() : ViewModelBase<ArgDefault>() {
    val text = MutableLiveData<String>().apply {
        value = byteCountToDisplaySizeTwo(600)
    }
}
