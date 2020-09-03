package com.hiwitech.android.detail.dialog

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.detail.R
import com.hiwitech.android.detail.BR
import com.hiwitech.android.detail.databinding.FragmentDetailBinding
import com.hiwitech.android.detail.viewmodel.ViewModelDetail
import com.hiwitech.android.mvvm.base.BaseDialogFragment
import com.hiwitech.android.shared.arg.ArgText
import com.hiwitech.android.shared.route.RoutePath

@Route(path = RoutePath.DIALOGFRAGMENT_DETAIL)
class DialogFragmentDetail : BaseDialogFragment<FragmentDetailBinding, ViewModelDetail, ArgText>() {

    override fun setLayoutId(): Int = R.layout.fragment_detail

    override fun bindVariableId(): Int = BR.viewModel

}