package com.hiwitech.android.developer.ui.detail.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.shared.base.ActivityBase
import com.hiwitech.android.shared.route.RoutePath

@Route(path = RoutePath.ACTIVITY_DETAIL)
class ActivityDetail : ActivityBase() {

    override fun getRoute(): String = RoutePath.FRAGMENT_DETAIL
}
