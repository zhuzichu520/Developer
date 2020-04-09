package com.hiwitech.android.developer.ui.category.fragment

import com.hiwitech.android.developer.BR
import com.hiwitech.android.developer.R
import com.hiwitech.android.developer.base.FragmentBase
import com.hiwitech.android.developer.databinding.FragmentCategoryBinding
import com.hiwitech.android.developer.ui.category.viewmodel.ViewModelCategory
import com.hiwitech.android.mvvm.base.ArgDefault

class FragmentCategory :
    FragmentBase<FragmentCategoryBinding, ViewModelCategory, ArgDefault>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_category
}
