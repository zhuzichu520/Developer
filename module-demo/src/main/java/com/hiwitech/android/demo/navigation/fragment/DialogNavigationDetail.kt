package com.hiwitech.android.demo.navigation.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.demo.R
import com.hiwitech.android.demo.BR
import com.hiwitech.android.demo.databinding.FragmentDemoBinding
import com.hiwitech.android.demo.navigation.arg.ArgDetail
import com.hiwitech.android.demo.navigation.viewmodel.ViewModelNavigationDetail
import com.hiwitech.android.shared.base.DialogFragmentBase
import com.hiwitech.android.shared.base.FragmentBase
import com.hiwitech.android.shared.route.RoutePath

@Route(path = RoutePath.Demo.DIALOG_NAVIGATION_DETAIL)
class DialogNavigationDetail : DialogFragmentBase<FragmentDemoBinding, ViewModelNavigationDetail, ArgDetail>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_navigation_detail

}
