package com.hiwitech.android.category.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.category.R
import com.hiwitech.android.category.BR
import com.hiwitech.android.category.databinding.FragmentCategoryBinding
import com.hiwitech.android.category.viewmodel.ViewModelCategory
import com.hiwitech.android.shared.base.FragmentBase
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.shared.route.RoutePath

@Route(path = RoutePath.Category.FRAGMENT_CATEGORY_MAIN)
class FragmentCategory :
    FragmentBase<FragmentCategoryBinding, ViewModelCategory, ArgDefault>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_category

}
