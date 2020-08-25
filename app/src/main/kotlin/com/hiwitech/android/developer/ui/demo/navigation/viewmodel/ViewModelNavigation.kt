package com.hiwitech.android.developer.ui.demo.navigation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hiwitech.android.developer.R
import com.hiwitech.android.shared.base.ViewModelBase
import com.hiwitech.android.developer.ui.detail.arg.ArgText
import com.hiwitech.android.libs.tool.encodeBase64
import com.hiwitech.android.libs.tool.object2Json
import com.hiwitech.android.mvvm.Mvvm
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.mvvm.event.SingleLiveEvent
import com.hiwitech.android.mvvm.ext.createCommand
import com.hiwitech.android.shared.route.RoutePath
import javax.inject.Inject

class ViewModelNavigation @Inject constructor() : ViewModelBase<ArgDefault>() {

    val onClickDeepLinkEvent = SingleLiveEvent<Unit>()

    val onStartFragmentEvent = createCommand {
        navigate(
            RoutePath.FRAGMENT_DETAIL,
            ArgText("Fragment 一页书：世事如棋，乾坤莫测，笑尽英雄啊！！")
        )
    }

    val onStartActivityCommand = createCommand {
        navigate(RoutePath.ACTIVITY_DETAIL, ArgText("Activity 一页书：世事如棋，乾坤莫测，笑尽英雄啊！！"))
    }

    val onStartDialogFragmentEvent = createCommand {
        navigate(RoutePath.DIALOGFRAGMENT_DETAIL, ArgText("Activity 一页书：世事如棋，乾坤莫测，笑尽英雄啊！！"))
    }

    val onStartDialogBottomFragmentEvent = createCommand {
        navigate(RoutePath.DIALOGFRAGMENT_CHOOSETHEME)
    }

    val text = MutableLiveData<String>().apply {
        value = "点击Url跳转Activity：https://www.zhuzichu.com/content/".plus(
            encodeBase64(
                object2Json(ArgText("Url跳转 一页书:世事如棋，乾坤莫测，笑尽英雄啊！！"))
            )
        )
    }

    val onStartDeepLinkEvent = createCommand {
        onClickDeepLinkEvent.call()
    }

    val onAnimNoEvent = createCommand {
        Mvvm.setAnimBuilder(
            R.anim.no_anim,
            R.anim.no_anim,
            R.anim.no_anim,
            R.anim.no_anim
        )
    }

    val onAnimHorizontalEvent = createCommand {
        Mvvm.setAnimBuilder(
            R.anim.h_enter,
            R.anim.h_exit,
            R.anim.h_pop_enter,
            R.anim.h_pop_exit
        )
    }

    val onAnimVerticalEvent = createCommand {
        Mvvm.setAnimBuilder(
            R.anim.v_enter,
            R.anim.v_exit,
            R.anim.v_pop_enter,
            R.anim.v_pop_exit
        )
    }
}
