package com.hiwitech.android.developer.ui.demo.navigation.fragment

import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.developer.BR
import com.hiwitech.android.developer.R
import com.hiwitech.android.developer.base.FragmentBase
import com.hiwitech.android.developer.databinding.FragmentNavigationBinding
import com.hiwitech.android.developer.ui.demo.navigation.viewmodel.ViewModelNavigation
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.shared.route.RoutePath

@Route(path = RoutePath.FRAGMENT_NAVIGATION)
class FragmentNavigation :
    FragmentBase<FragmentNavigationBinding, ViewModelNavigation, ArgDefault>() {


    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_navigation

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.onClickDeepLinkEvent.observe(
            viewLifecycleOwner,
            Observer {

            }
        )
    }
}
