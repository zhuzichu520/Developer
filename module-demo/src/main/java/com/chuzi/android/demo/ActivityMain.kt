package com.chuzi.android.demo

import com.alibaba.android.arouter.facade.annotation.Route
import com.chuzi.android.shared.base.ActivityBase
import com.chuzi.android.shared.route.RoutePath

/**
 * desc
 * author: 朱子楚
 * time: 2020/9/9 11:41 AM
 * since: v 1.0.0
 */
@Route(path = RoutePath.Demo.ACTIVITY_DEMO_MAIN)
class ActivityMain : ActivityBase() {
    override fun getRoute(): String = RoutePath.Demo.FRAGMENT_DEMO_MAIN
}