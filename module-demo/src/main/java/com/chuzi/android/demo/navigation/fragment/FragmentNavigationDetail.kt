package com.chuzi.android.demo.navigation.fragment

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.chuzi.android.demo.R
import com.chuzi.android.demo.BR
import com.chuzi.android.demo.databinding.FragmentNavigationDetailBinding
import com.chuzi.android.demo.navigation.arg.ArgDetail
import com.chuzi.android.demo.navigation.viewmodel.ViewModelNavigationDetail
import com.chuzi.android.shared.base.FragmentBase
import com.chuzi.android.shared.route.RoutePath
import com.chuzi.android.widget.log.lumberjack.L

@Route(path = RoutePath.Demo.FRAGMENT_NAVIGATION_DETAIL)
class FragmentNavigationDetail :
    FragmentBase<FragmentNavigationDetailBinding, ViewModelNavigationDetail, ArgDetail>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_navigation_detail

    override fun initView() {
        super.initView()
        initTopBar()
    }

    private fun initTopBar() {
        binding.topbar.addLeftImageButton(R.drawable.ic_topbar_back, R.id.topbar_left_back_button)
            .setOnClickListener {
                back()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        L.tag("FragmentNavigationDetail").i { "FragmentNavigationDetail---onCreate" }
    }

    override fun onCreateView(): View? {
        L.tag("FragmentNavigationDetail").i { "FragmentNavigationDetail---onCreateView" }
        return super.onCreateView()
    }

    override fun onStart() {
        super.onStart()
        L.tag("FragmentNavigationDetail").i { "FragmentNavigationDetail---onStart" }
    }

    override fun onResume() {
        super.onResume()
        L.tag("FragmentNavigationDetail").i { "FragmentNavigationDetail---onResume" }
    }

    override fun onPause() {
        super.onPause()
        L.tag("FragmentNavigationDetail").i { "FragmentNavigationDetail---onPause" }
    }

    override fun onStop() {
        super.onStop()
        L.tag("FragmentNavigationDetail").i { "FragmentNavigationDetail---onStop" }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        L.tag("FragmentNavigationDetail").i { "FragmentNavigationDetail---onDestroyView" }
    }

    override fun onDestroy() {
        super.onDestroy()
        L.tag("FragmentNavigationDetail").i { "FragmentNavigationDetail---onDestroy" }
    }

}
