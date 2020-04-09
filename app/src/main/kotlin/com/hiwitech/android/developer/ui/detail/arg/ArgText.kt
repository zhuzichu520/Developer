package com.hiwitech.android.developer.ui.detail.arg

import com.hiwitech.android.mvvm.base.BaseArg
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArgText(
    var content: String? = null
) : BaseArg()
