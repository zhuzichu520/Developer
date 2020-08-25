package com.hiwitech.android.developer.ui.detail.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.developer.BR
import com.hiwitech.android.developer.R
import com.hiwitech.android.shared.base.FragmentBase
import com.hiwitech.android.developer.databinding.FragmentDetailBinding
import com.hiwitech.android.developer.ui.detail.arg.ArgText
import com.hiwitech.android.developer.ui.detail.viewmodel.ViewModelDetail
import com.hiwitech.android.shared.route.RoutePath

@Route(path = RoutePath.FRAGMENT_DETAIL)
class FragmentDetail : FragmentBase<FragmentDetailBinding, ViewModelDetail, ArgText>() {

    override fun setLayoutId(): Int = R.layout.fragment_detail

    override fun bindVariableId(): Int = BR.viewModel

}
