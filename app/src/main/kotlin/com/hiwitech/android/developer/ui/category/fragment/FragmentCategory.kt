package com.hiwitech.android.developer.ui.category.fragment

import com.hiwitech.android.developer.BR
import com.hiwitech.android.developer.R
import com.hiwitech.android.developer.base.FragmentBase
import com.hiwitech.android.developer.databinding.FragmentCategoryBinding
import com.hiwitech.android.developer.ui.category.viewmodel.ViewModelCategory
import com.hiwitech.android.libs.tool.jumpMarket
import com.hiwitech.android.mvvm.base.ArgDefault
import kotlinx.android.synthetic.main.fragment_category.*

class FragmentCategory :
    FragmentBase<FragmentCategoryBinding, ViewModelCategory, ArgDefault>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_category

    override fun initListener() {
        super.initListener()
        click.setOnClickListener {
//            jumpShareText(requireContext(),"分享文本到","一剑凌情愿")
//            jumpEmail(requireContext(),"emial打开","524787275@qq.com")
            jumpMarket(requireContext(),"Market")
        }
    }
}
