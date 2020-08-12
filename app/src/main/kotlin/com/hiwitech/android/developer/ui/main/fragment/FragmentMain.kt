package com.hiwitech.android.developer.ui.main.fragment

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.developer.BR
import com.hiwitech.android.developer.R
import com.hiwitech.android.developer.base.FragmentBase
import com.hiwitech.android.developer.databinding.FragmentMainBinding
import com.hiwitech.android.developer.ui.category.fragment.FragmentCategory
import com.hiwitech.android.developer.ui.demo.main.fragment.FragmentDemo
import com.hiwitech.android.developer.ui.home.fragment.FragmentHome
import com.hiwitech.android.developer.ui.main.viewmodel.ViewModelMain
import com.hiwitech.android.developer.ui.me.fragment.FragmentMe
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.shared.base.DefaultIntFragmentPagerAdapter
import com.hiwitech.android.shared.ext.plusBadge
import com.hiwitech.android.shared.ext.setupWithViewPager
import com.hiwitech.android.shared.ext.toast
import com.hiwitech.android.shared.route.RoutePath
import com.hiwitech.android.widget.badge.Badge
import kotlinx.android.synthetic.main.fragment_main.*

@Route(path = RoutePath.FRAGMENT_MAIN)
class FragmentMain : FragmentBase<FragmentMainBinding, ViewModelMain, ArgDefault>() {

    private val waitTime = 2000L
    private var touchTime: Long = 0
    private var badge: Badge? = null

    override fun setLayoutId(): Int = R.layout.fragment_main

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        val fragments = listOf<Fragment>(
            FragmentDemo(),
            FragmentHome(),
            FragmentCategory(),
            FragmentMe()
        )

        val titles = listOf(
            R.string.demo,
            R.string.home,
            R.string.category,
            R.string.me
        )

        content.adapter = DefaultIntFragmentPagerAdapter(childFragmentManager, fragments, titles)
        bottom.setupWithViewPager(content)
        badge = bottom.plusBadge(0)
        badge?.badgeNumber = 10
        badge?.setOnDragStateChangedListener { _, _, _ ->
        }
    }

//    private fun initBackListener() {
//        requireActivity().onBackPressedDispatcher.addCallback(this) {
//            if (System.currentTimeMillis() - touchTime < waitTime) {
//                // 退出app并清除任务栈
//                requireActivity().finish()
//            } else {
//                touchTime = System.currentTimeMillis()
//                R.string.press_again_to_exit.toast()
//            }
//        }
//    }
}
