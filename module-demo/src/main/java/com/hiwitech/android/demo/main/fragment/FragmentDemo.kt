package com.hiwitech.android.demo.main.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.demo.R
import com.hiwitech.android.demo.BR
import com.hiwitech.android.demo.databinding.FragmentDemoBinding
import com.hiwitech.android.demo.main.viewmodel.ViewModelDemo
import com.hiwitech.android.shared.base.FragmentBase
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.shared.route.RoutePath

@Route(path = RoutePath.Demo.FRAGMENT_DEMO_MAIN)
class FragmentDemo : FragmentBase<FragmentDemoBinding, ViewModelDemo, ArgDefault>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_demo
}
