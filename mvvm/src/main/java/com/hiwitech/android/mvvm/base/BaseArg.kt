package com.hiwitech.android.mvvm.base

import android.os.Parcelable
import com.hiwitech.android.mvvm.Mvvm

/**
 * desc 页面参数基类
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
abstract class BaseArg(
    var useSystemAnimation: Boolean? = false,
    var enterAnim: Int? = Mvvm.enterAnim,
    var exitAnim: Int? = Mvvm.exitAnim,
    var popEnterAnim: Int? = Mvvm.popEnterAnim,
    var popExitAnim: Int? = Mvvm.popExitAnim
) : Parcelable