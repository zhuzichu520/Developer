package com.hiwitech.android.demo.navigation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hiwitech.android.demo.navigation.arg.ArgDetail
import com.hiwitech.android.shared.base.ViewModelBase

/**
 * desc
 * author: 朱子楚
 * time: 2020/11/26 4:20 PM
 * since: v 1.0.0
 */
class ViewModelNavigationDetail : ViewModelBase<ArgDetail>() {

    val info = MutableLiveData<Int>()

    override fun initArgs(arg: ArgDetail) {
        super.initArgs(arg)
        info.value = arg.resId
    }
}