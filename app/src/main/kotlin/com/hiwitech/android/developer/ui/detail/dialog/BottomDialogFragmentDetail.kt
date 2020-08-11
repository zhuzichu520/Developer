package com.hiwitech.android.developer.ui.detail.dialog

import com.hiwitech.android.developer.BR
import com.hiwitech.android.developer.R
import com.hiwitech.android.developer.databinding.FragmentDetailBinding
import com.hiwitech.android.developer.ui.detail.arg.ArgText
import com.hiwitech.android.developer.ui.detail.viewmodel.ViewModelDetail
import com.hiwitech.android.mvvm.base.BaseBottomDialogFragment

class BottomDialogFragmentDetail : BaseBottomDialogFragment<FragmentDetailBinding, ViewModelDetail, ArgText>() {

    override fun setLayoutId(): Int = R.layout.fragment_detail

    override fun bindVariableId(): Int = BR.viewModel

}
