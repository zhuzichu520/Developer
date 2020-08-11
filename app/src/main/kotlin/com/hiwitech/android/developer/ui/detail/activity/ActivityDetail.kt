package com.hiwitech.android.developer.ui.detail.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.developer.R
import com.hiwitech.android.developer.base.ActivityBase

@Route(path = ActivityDetail.ROUTE)
class ActivityDetail : ActivityBase() {

    companion object {
        const val ROUTE = "/activity/detail"
    }

    override fun setNavGraph(): Int = R.navigation.navigation_detail

}
