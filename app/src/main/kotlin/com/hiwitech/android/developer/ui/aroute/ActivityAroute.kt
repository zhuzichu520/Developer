package com.hiwitech.android.developer.ui.aroute

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.shared.base.ActivityBase
import com.hiwitech.android.shared.route.RoutePath

/**
 * desc
 * author: 朱子楚
 * time: 2020/8/11 10:25 AM
 * since: v 1.0.0
 */
@Route(path = RoutePath.ACTIVITY_AROUTE)
class ActivityAroute : ActivityBase() {

    override fun getRoute(): String = RoutePath.FRAGMENT_ROUTE

}