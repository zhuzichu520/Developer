package com.hiwitech.android.me.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.me.R
import com.hiwitech.android.me.BR
import com.hiwitech.android.me.databinding.FragmentMeBinding
import com.hiwitech.android.me.viewmodel.ViewModelMe
import com.hiwitech.android.shared.base.FragmentBase
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.shared.route.RoutePath

@Route(path = RoutePath.FRAGMENT_ME)
class FragmentMe : FragmentBase<FragmentMeBinding, ViewModelMe, ArgDefault>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_me
}
