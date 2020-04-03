package com.hiwitech.android.developer.ui.demo.main.fragment

import com.hiwitech.android.mvvm.base.BaseFragment
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.developer.BR
import com.hiwitech.android.developer.R
import com.hiwitech.android.developer.databinding.FragmentDemoBinding
import com.hiwitech.android.developer.ui.demo.main.viewmodel.ViewModelDemo

class FragmentDemo : BaseFragment<FragmentDemoBinding, ViewModelDemo, ArgDefault>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_demo

}