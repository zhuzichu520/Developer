package com.hiwitech.android.developer.rxhttp

import com.hiwitech.android.developer.BuildConfig
import rxhttp.wrapper.annotation.DefaultDomain

object Url {
    @DefaultDomain
    const val baseUrl = BuildConfig.HOST_APP2
}
