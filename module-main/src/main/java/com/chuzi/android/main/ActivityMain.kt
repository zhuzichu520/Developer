package com.chuzi.android.main

import com.alibaba.android.arouter.facade.annotation.Route
import com.chuzi.android.shared.base.ActivityBase
import com.chuzi.android.shared.route.RoutePath

/**
 * desc
 * author: 朱子楚
 * time: 2020/9/9 11:41 AM
 * since: v 1.0.0
 */

@Route(path = RoutePath.Main.ACTIVITY_MAIN_MAIN)
class ActivityMain : ActivityBase() {

    override fun getRoute(): String = RoutePath.Main.FRAGMENT_MAIN_MAIN

}