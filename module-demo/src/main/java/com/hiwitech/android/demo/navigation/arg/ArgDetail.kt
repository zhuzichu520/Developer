package com.hiwitech.android.demo.navigation.arg

import androidx.annotation.StringRes
import com.hiwitech.android.mvvm.base.BaseArg

data class ArgDetail(
        @StringRes val resId: Int
) : BaseArg()
