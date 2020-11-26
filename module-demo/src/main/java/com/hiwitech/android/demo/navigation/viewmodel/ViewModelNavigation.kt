package com.hiwitech.android.demo.navigation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hiwitech.android.demo.R
import com.hiwitech.android.demo.BR
import com.hiwitech.android.demo.navigation.arg.ArgDetail
import com.hiwitech.android.shared.base.ViewModelBase
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.shared.ext.map
import com.hiwitech.android.shared.route.RoutePath
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass

class ViewModelNavigation : ViewModelBase<ArgDefault>() {

    val items = MutableLiveData<List<Any>>().apply {
        value = listOf(
                ItemViewModelNavigation(
                        this@ViewModelNavigation,
                        R.string.demo_navigation_activity
                ) {
                    navigate(RoutePath.Demo.ACTIVITY_NAVIGATION_DETAIL, ArgDetail(R.string.demo_navigation_activity))
                },
                ItemViewModelNavigation(
                        this@ViewModelNavigation,
                        R.string.demo_navigation_fragment
                ) {
                    navigate(RoutePath.Demo.FRAGMENT_NAVIGATION_DETAIL, ArgDetail(R.string.demo_navigation_fragment))
                },
                ItemViewModelNavigation(
                        this@ViewModelNavigation,
                        R.string.demo_navigation_dialog
                ) {
                    navigate(RoutePath.Demo.DIALOG_NAVIGATION_DETAIL, ArgDetail(R.string.demo_navigation_dialog))
                },
        )
    }

    val itemBinding = OnItemBindClass<Any>().apply {
        map<ItemViewModelNavigation>(BR.item, R.layout.item_navigation)
    }

}
