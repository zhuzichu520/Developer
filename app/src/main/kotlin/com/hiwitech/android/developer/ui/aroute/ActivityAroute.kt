package com.hiwitech.android.developer.ui.aroute

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.mvvm.base.BaseActivity2

/**
 * desc
 * author: 朱子楚
 * time: 2020/8/11 10:25 AM
 * since: v 1.0.0
 */
@Route(path = ActivityAroute.ROUTE)
class ActivityAroute : BaseActivity2() {

    companion object {
        const val ROUTE = "/activity/aroute"
    }

    override fun getRoute(): String = FragmentRoute.ROUTE

}