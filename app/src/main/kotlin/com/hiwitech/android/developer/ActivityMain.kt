package com.hiwitech.android.developer

import android.os.Bundle
import com.hiwitech.android.shared.base.ActivityBase
import com.hiwitech.android.shared.route.RoutePath

class ActivityMain : ActivityBase() {
    override fun getRoute(): String = RoutePath.FRAGMENT_MAIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}
