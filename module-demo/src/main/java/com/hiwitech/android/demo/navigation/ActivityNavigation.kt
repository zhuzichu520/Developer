package com.hiwitech.android.demo.navigation

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.shared.base.ActivityBase
import com.hiwitech.android.shared.route.RoutePath

/**
 * desc
 * author: 朱子楚
 * time: 2020/9/9 11:41 AM
 * since: v 1.0.0
 */
@Route(path = RoutePath.Demo.ACTIVITY_NAVIGATION_DETAIL)
class ActivityNavigation : ActivityBase() {
    override fun getRoute(): String = RoutePath.Demo.FRAGMENT_NAVIGATION_DETAIL
}