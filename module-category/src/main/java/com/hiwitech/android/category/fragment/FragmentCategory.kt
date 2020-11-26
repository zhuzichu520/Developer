package com.hiwitech.android.category.fragment

import android.graphics.Color
import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.category.R
import com.hiwitech.android.category.BR
import com.hiwitech.android.category.databinding.FragmentCategoryBinding
import com.hiwitech.android.category.viewmodel.ViewModelCategory
import com.hiwitech.android.libs.tool.jumpEmail
import com.hiwitech.android.shared.base.FragmentBase
import com.hiwitech.android.libs.tool.jumpMarket
import com.hiwitech.android.libs.tool.jumpShareText
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.shared.route.RoutePath

@Route(path = RoutePath.Category.FRAGMENT_CATEGORY_MAIN)
class FragmentCategory :
    FragmentBase<FragmentCategoryBinding, ViewModelCategory, ArgDefault>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_category

    override fun initListener() {
        super.initListener()
        binding.click.shadowColor = Color.parseColor("#ff0000")
        binding.click.setOnClickListener {
            0 / 0
            jumpShareText(requireContext(),"分享文本到","一剑凌情愿")
            jumpEmail(requireContext(),"emial打开","524787275@qq.com")
            jumpMarket(requireContext(), "Market")
        }
    }
}
