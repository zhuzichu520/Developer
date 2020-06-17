package com.hiwitech.android.developer.ui.demo.navigation.fragment

import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.hiwitech.android.developer.BR
import com.hiwitech.android.developer.R
import com.hiwitech.android.developer.databinding.FragmentNavigationBinding
import com.hiwitech.android.developer.ui.demo.navigation.viewmodel.ViewModelNavigation
import com.hiwitech.android.developer.ui.detail.arg.ArgText
import com.hiwitech.android.mvvm.Mvvm
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.mvvm.base.BaseFragment
import com.hiwitech.android.widget.notify.Notify

class FragmentNavigation :
    BaseFragment<FragmentNavigationBinding, ViewModelNavigation, ArgDefault>() {


    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_navigation

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.onClickDeepLinkEvent.observe(
            viewLifecycleOwner,
            Observer {
                val pendingIntent = navController.createDeepLink()
                    .setGraph(R.navigation.navigation_main)
                    .setDestination(R.id.fragmentDetail)
                    .setArguments(bundleOf(Mvvm.KEY_ARG to ArgText("DeepLink之通知跳转 一页书：世事如棋，乾坤莫测，笑尽英雄啊！！")))
                    .createPendingIntent()
                Notify.with(requireContext())
                    .meta {
                        clickIntent = pendingIntent
                    }
                    .content {
                        title = "有一条新消息"
                        text = "Deeplink跳转到切换语言界面"
                    }
                    .show(1)
            }
        )
    }
}
