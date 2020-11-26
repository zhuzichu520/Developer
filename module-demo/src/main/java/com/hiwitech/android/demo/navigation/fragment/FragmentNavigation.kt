package com.hiwitech.android.demo.navigation.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.demo.R
import com.hiwitech.android.demo.BR
import com.hiwitech.android.demo.databinding.FragmentNavigationBinding
import com.hiwitech.android.demo.navigation.viewmodel.ViewModelNavigation
import com.hiwitech.android.shared.base.FragmentBase
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.shared.route.RoutePath

@Route(path = RoutePath.Demo.FRAGMENT_NAVIGATION_MAIN)
class FragmentNavigation : FragmentBase<FragmentNavigationBinding, ViewModelNavigation, ArgDefault>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_navigation

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
