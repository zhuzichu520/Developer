package com.hiwitech.android.developer.ui.me.fragment

import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.mvvm.base.BaseFragment
import com.hiwitech.android.developer.BR
import com.hiwitech.android.developer.R
import com.hiwitech.android.developer.databinding.FragmentMeBinding
import com.hiwitech.android.developer.ui.me.viewmodel.ViewModelMe

class FragmentMe : BaseFragment<FragmentMeBinding, ViewModelMe, ArgDefault>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_me
}