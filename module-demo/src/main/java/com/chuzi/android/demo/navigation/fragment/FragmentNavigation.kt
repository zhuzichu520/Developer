package com.chuzi.android.demo.navigation.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.chuzi.android.demo.R
import com.chuzi.android.demo.BR
import com.chuzi.android.demo.databinding.FragmentNavigationBinding
import com.chuzi.android.demo.navigation.viewmodel.ViewModelNavigation
import com.chuzi.android.shared.base.FragmentBase
import com.chuzi.android.mvvm.base.ArgDefault
import com.chuzi.android.shared.route.RoutePath

@Route(path = RoutePath.Demo.FRAGMENT_NAVIGATION_MAIN)
class FragmentNavigation :
    FragmentBase<FragmentNavigationBinding, ViewModelNavigation, ArgDefault>() {

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
