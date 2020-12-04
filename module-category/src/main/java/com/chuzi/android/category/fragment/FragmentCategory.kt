package com.chuzi.android.category.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.chuzi.android.category.R
import com.chuzi.android.category.BR
import com.chuzi.android.category.databinding.FragmentCategoryBinding
import com.chuzi.android.category.viewmodel.ViewModelCategory
import com.chuzi.android.shared.base.FragmentBase
import com.chuzi.android.mvvm.base.ArgDefault
import com.chuzi.android.shared.route.RoutePath

@Route(path = RoutePath.Category.FRAGMENT_CATEGORY_MAIN)
class FragmentCategory :
    FragmentBase<FragmentCategoryBinding, ViewModelCategory, ArgDefault>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_category

}
