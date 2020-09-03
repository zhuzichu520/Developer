package com.hiwitech.android.detail.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.detail.R
import com.hiwitech.android.detail.BR
import com.hiwitech.android.detail.databinding.FragmentDetailBinding
import com.hiwitech.android.detail.viewmodel.ViewModelDetail
import com.hiwitech.android.shared.arg.ArgText
import com.hiwitech.android.shared.base.FragmentBase
import com.hiwitech.android.shared.route.RoutePath

@Route(path = RoutePath.FRAGMENT_DETAIL)
class FragmentDetail : FragmentBase<FragmentDetailBinding, ViewModelDetail, ArgText>() {

    override fun setLayoutId(): Int = R.layout.fragment_detail

    override fun bindVariableId(): Int = BR.viewModel

}