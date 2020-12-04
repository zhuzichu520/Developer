package com.chuzi.android.shared.rxhttp

import com.chuzi.android.shared.BuildConfig
import rxhttp.wrapper.annotation.DefaultDomain

object Url {
    @DefaultDomain
    const val baseUrl = BuildConfig.HOST_APP2
}
