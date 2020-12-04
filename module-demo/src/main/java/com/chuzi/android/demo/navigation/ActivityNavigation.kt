package com.chuzi.android.demo.navigation

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.chuzi.android.shared.base.ActivityBase
import com.chuzi.android.shared.route.RoutePath
import com.chuzi.android.widget.log.lumberjack.L

/**
 * desc
 * author: 朱子楚
 * time: 2020/9/9 11:41 AM
 * since: v 1.0.0
 */
@Route(path = RoutePath.Demo.ACTIVITY_NAVIGATION_DETAIL)
class ActivityNavigation : ActivityBase() {
    override fun getRoute(): String = RoutePath.Demo.FRAGMENT_NAVIGATION_DETAIL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        L.tag("FragmentNavigationDetail").i { "ActivityNavigation---onCreate" }
    }

    override fun onStart() {
        super.onStart()
        L.tag("FragmentNavigationDetail").i { "ActivityNavigation---onStart" }
    }

    override fun onResume() {
        super.onResume()
        L.tag("FragmentNavigationDetail").i { "ActivityNavigation---onResume" }
    }

    override fun onPause() {
        super.onPause()
        L.tag("FragmentNavigationDetail").i { "ActivityNavigation---onPause" }
    }

    override fun onStop() {
        super.onStop()
        L.tag("FragmentNavigationDetail").i { "ActivityNavigation---onStop" }
    }

    override fun onDestroy() {
        super.onDestroy()
        L.tag("FragmentNavigationDetail").i { "ActivityNavigation---onDestroy" }
    }
}