package com.chuzi.android.demo.navigation.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.chuzi.android.demo.R
import com.chuzi.android.demo.BR
import com.chuzi.android.demo.databinding.FragmentDemoBinding
import com.chuzi.android.demo.navigation.arg.ArgDetail
import com.chuzi.android.demo.navigation.viewmodel.ViewModelNavigationDetail
import com.chuzi.android.shared.base.DialogFragmentBase
import com.chuzi.android.shared.route.RoutePath

@Route(path = RoutePath.Demo.DIALOG_NAVIGATION_DETAIL)
class DialogNavigationDetail : DialogFragmentBase<FragmentDemoBinding, ViewModelNavigationDetail, ArgDetail>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_navigation_detail

}
