package com.chuzi.android.demo.navigation.arg

import androidx.annotation.StringRes
import com.chuzi.android.mvvm.base.BaseArg

data class ArgDetail(
        @StringRes val resId: Int
) : BaseArg()
