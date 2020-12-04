package com.chuzi.android.category.viewmodel

import androidx.lifecycle.MutableLiveData
import com.chuzi.android.shared.base.ViewModelBase
import com.chuzi.android.libs.tool.byteCountToDisplaySizeTwo
import com.chuzi.android.mvvm.base.ArgDefault

class ViewModelCategory : ViewModelBase<ArgDefault>() {
    val text = MutableLiveData<String>().apply {
        value = byteCountToDisplaySizeTwo(600)
    }
}
