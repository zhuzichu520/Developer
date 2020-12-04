package com.chuzi.android.home

import com.alibaba.android.arouter.facade.annotation.Route
import com.chuzi.android.shared.base.ActivityBase
import com.chuzi.android.shared.route.RoutePath

/**
 * desc
 * author: 朱子楚
 * time: 2020/9/9 11:41 AM
 * since: v 1.0.0
 */

@Route(path = RoutePath.Home.ACTIVITY_HOME_MAIN)
class ActivityMain : ActivityBase() {

    override fun getRoute(): String = RoutePath.Home.FRAGMENT_HOME_MAIN

}