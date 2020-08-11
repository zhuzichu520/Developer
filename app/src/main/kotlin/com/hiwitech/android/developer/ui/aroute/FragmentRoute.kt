package com.hiwitech.android.developer.ui.aroute

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.developer.BR
import com.hiwitech.android.developer.R
import com.hiwitech.android.developer.databinding.FragmentArouteBinding
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.mvvm.base.BaseFragment2

/**
 * desc
 * author: 朱子楚
 * time: 2020/8/11 1:50 PM
 * since: v 1.0.0
 */

@Route(path = FragmentRoute.ROUTE)
class FragmentRoute : BaseFragment2<FragmentArouteBinding, ViewModelRoute, ArgDefault>() {

    companion object {
        const val ROUTE = "/fragment/aroute"
    }

    override fun setLayoutId(): Int = R.layout.fragment_aroute

    override fun bindVariableId(): Int = BR.viewModel

}