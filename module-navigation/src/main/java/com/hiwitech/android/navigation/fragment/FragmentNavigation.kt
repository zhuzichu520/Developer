package com.hiwitech.android.navigation.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.shared.base.FragmentBase
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.navigation.R
import com.hiwitech.android.navigation.BR
import com.hiwitech.android.navigation.databinding.FragmentNavigationBinding
import com.hiwitech.android.navigation.viewmodel.ViewModelNavigation
import com.hiwitech.android.shared.route.RoutePath

@Route(path = RoutePath.FRAGMENT_NAVIGATION)
class FragmentNavigation :
    FragmentBase<FragmentNavigationBinding, ViewModelNavigation, ArgDefault>() {


    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_navigation

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.onClickDeepLinkEvent.observe(viewLifecycleOwner) {

        }
    }
}
