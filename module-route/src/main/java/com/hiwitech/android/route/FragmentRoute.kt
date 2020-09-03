package com.hiwitech.android.route

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.shared.base.FragmentBase
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.route.databinding.FragmentArouteBinding
import com.hiwitech.android.shared.route.RoutePath
import kotlinx.android.synthetic.main.fragment_aroute.*

/**
 * desc
 * author: 朱子楚
 * time: 2020/8/11 1:50 PM
 * since: v 1.0.0
 */

@Route(path = RoutePath.FRAGMENT_ROUTE)
class FragmentRoute : FragmentBase<FragmentArouteBinding, ViewModelRoute, ArgDefault>() {

    override fun setLayoutId(): Int = R.layout.fragment_aroute

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        super.initView()
        back.setOnClickListener {
            navigate(RoutePath.FRAGMENT_ROUTE)
        }
    }
}