package com.hiwitech.android.main.fragment

import android.graphics.Typeface
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hiwitech.android.shared.base.FragmentBase
import com.hiwitech.android.libs.tool.toCast
import com.hiwitech.android.main.R
import com.hiwitech.android.main.BR
import com.hiwitech.android.main.databinding.FragmentMainBinding
import com.hiwitech.android.main.viewmodel.ViewModelMain
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.shared.base.DefaultIntFragmentPagerAdapter
import com.hiwitech.android.shared.ext.toDrawableByResId
import com.hiwitech.android.shared.ext.toStringByResId
import com.hiwitech.android.shared.route.RoutePath
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder
import kotlinx.android.synthetic.main.fragment_main.*


@Route(path = RoutePath.FRAGMENT_MAIN)
class FragmentMain : FragmentBase<FragmentMainBinding, ViewModelMain, ArgDefault>() {

    override fun setLayoutId(): Int = R.layout.fragment_main

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        val fragments = listOf<Fragment>(
            ARouter.getInstance().build(RoutePath.FRAGMENT_DEMO)
                .navigation().toCast(),
            ARouter.getInstance().build(RoutePath.FRAGMENT_HOME)
                .navigation().toCast(),
            ARouter.getInstance().build(RoutePath.FRAGMENT_CATEGORY)
                .navigation().toCast(),
            ARouter.getInstance().build(RoutePath.FRAGMENT_ME)
                .navigation().toCast()
        )
        initTabs()
        pager.offscreenPageLimit = fragments.size
        pager.adapter = DefaultIntFragmentPagerAdapter(parentFragmentManager, fragments)
        tabs.setupWithViewPager(pager, false)
    }

    private fun initTabs() {
        val builder: QMUITabBuilder = tabs.tabBuilder()
        builder.setTypeface(null, Typeface.DEFAULT_BOLD)
        builder.setSelectedIconScale(1.2f)
            .setTextSize(
                QMUIDisplayHelper.sp2px(context, 13),
                QMUIDisplayHelper.sp2px(context, 15)
            )
            .setDynamicChangeIconColor(false)
        val demo = builder
            .setNormalDrawable(R.drawable.ic_main_demo.toDrawableByResId())
            .setSelectedDrawable(R.drawable.ic_main_demo.toDrawableByResId())
            .setText(R.string.demo.toStringByResId())
            .build(context)
        val home = builder
            .setNormalDrawable(R.drawable.ic_main_home.toDrawableByResId())
            .setSelectedDrawable(R.drawable.ic_main_home.toDrawableByResId())
            .setText(R.string.home.toStringByResId())
            .build(context)
        val category = builder
            .setNormalDrawable(R.drawable.ic_main_category.toDrawableByResId())
            .setSelectedDrawable(R.drawable.ic_main_category.toDrawableByResId())
            .setText(R.string.category.toStringByResId())
            .build(context)
        val me = builder
            .setNormalDrawable(R.drawable.ic_main_me.toDrawableByResId())
            .setSelectedDrawable(R.drawable.ic_main_me.toDrawableByResId())
            .setText(R.string.me.toStringByResId())
            .build(context)
        tabs.addTab(demo)
            .addTab(home)
            .addTab(category)
            .addTab(me)
    }

}
