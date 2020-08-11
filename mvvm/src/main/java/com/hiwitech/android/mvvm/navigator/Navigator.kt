package com.hiwitech.android.mvvm.navigator

import androidx.core.os.bundleOf
import com.alibaba.android.arouter.launcher.ARouter
import com.hiwitech.android.mvvm.Mvvm
import com.hiwitech.android.mvvm.base.BaseArg

/**
 * desc
 * author: 朱子楚
 * time: 2020/8/11 11:01 AM
 * since: v 1.0.0
 */
class Navigator {

    fun navigateActivity(route: String, arg: BaseArg) {
        ARouter.getInstance().build(route).with(bundleOf(Mvvm.KEY_ARG to arg)).navigation()
    }

    fun navigateFragment() {

    }

}