package com.hiwitech.android.shared.rxhttp

import com.hiwitech.android.shared.BuildConfig
import rxhttp.wrapper.annotation.DefaultDomain

object Url {
    @DefaultDomain
    const val baseUrl = BuildConfig.HOST_APP2
}
