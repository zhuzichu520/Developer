package com.chuzi.android.me.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.chuzi.android.me.R
import com.chuzi.android.me.BR
import com.chuzi.android.me.databinding.FragmentMeBinding
import com.chuzi.android.me.viewmodel.ViewModelMe
import com.chuzi.android.shared.base.FragmentBase
import com.chuzi.android.mvvm.base.ArgDefault
import com.chuzi.android.shared.route.RoutePath

@Route(path = RoutePath.Me.FRAGMENT_ME_MAIN)
class FragmentMe : FragmentBase<FragmentMeBinding, ViewModelMe, ArgDefault>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_me
}
