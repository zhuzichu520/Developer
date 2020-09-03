package com.hiwitech.android.category.fragment

import android.graphics.Color
import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.category.R
import com.hiwitech.android.category.BR
import com.hiwitech.android.category.databinding.FragmentCategoryBinding
import com.hiwitech.android.category.viewmodel.ViewModelCategory
import com.hiwitech.android.shared.base.FragmentBase
import com.hiwitech.android.libs.tool.jumpMarket
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.shared.route.RoutePath
import kotlinx.android.synthetic.main.fragment_category.*

@Route(path = RoutePath.FRAGMENT_CATEGORY)
class FragmentCategory :
    FragmentBase<FragmentCategoryBinding, ViewModelCategory, ArgDefault>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_category

    override fun initListener() {
        super.initListener()
        click.shadowColor= Color.parseColor("#ff0000")
        click.setOnClickListener {
//            jumpShareText(requireContext(),"分享文本到","一剑凌情愿")
//            jumpEmail(requireContext(),"emial打开","524787275@qq.com")
            jumpMarket(requireContext(),"Market")
        }
    }
}
