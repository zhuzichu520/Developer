package com.hiwitech.android.demo.navigation.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.demo.R
import com.hiwitech.android.demo.BR
import com.hiwitech.android.demo.databinding.FragmentNavigationDetailBinding
import com.hiwitech.android.demo.navigation.arg.ArgDetail
import com.hiwitech.android.demo.navigation.viewmodel.ViewModelNavigationDetail
import com.hiwitech.android.shared.base.FragmentBase
import com.hiwitech.android.shared.route.RoutePath

@Route(path = RoutePath.Demo.FRAGMENT_NAVIGATION_DETAIL)
class FragmentNavigationDetail : FragmentBase<FragmentNavigationDetailBinding, ViewModelNavigationDetail, ArgDetail>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_navigation_detail

    override fun initView() {
        super.initView()
        initTopBar()
    }

    private fun initTopBar() {
        binding.topbar.addLeftImageButton(R.drawable.ic_topbar_back, R.id.topbar_left_back_button)
                .setOnClickListener {
                    back()
                }
    }

}
