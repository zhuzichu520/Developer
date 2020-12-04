package com.chuzi.android.demo.main.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.chuzi.android.demo.R
import com.chuzi.android.demo.BR
import com.chuzi.android.demo.databinding.FragmentDemoBinding
import com.chuzi.android.demo.main.viewmodel.ViewModelDemo
import com.chuzi.android.shared.base.FragmentBase
import com.chuzi.android.mvvm.base.ArgDefault
import com.chuzi.android.shared.route.RoutePath

@Route(path = RoutePath.Demo.FRAGMENT_DEMO_MAIN)
class FragmentDemo : FragmentBase<FragmentDemoBinding, ViewModelDemo, ArgDefault>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_demo
}
