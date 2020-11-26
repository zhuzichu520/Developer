package com.hiwitech.android.demo.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hiwitech.android.demo.R
import com.hiwitech.android.demo.BR
import com.hiwitech.android.shared.base.ViewModelBase
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.shared.ext.map
import com.hiwitech.android.shared.route.RoutePath
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass

class ViewModelDemo : ViewModelBase<ArgDefault>() {

    companion object {
        const val TYPE_NAVIGATION = 0
    }

    private val closure: Int.() -> Unit = {
        when (this) {
            TYPE_NAVIGATION -> {
                navigate(RoutePath.Demo.FRAGMENT_NAVIGATION_MAIN)
            }
        }
    }

    val items = MutableLiveData<List<Any>>().apply {
        value = listOf(
                ItemViewModelDemo(
                        this@ViewModelDemo,
                        TYPE_NAVIGATION,
                        R.string.demo_navigation,
                        closure
                ),
        )
    }

    val itemBinding = OnItemBindClass<Any>().apply {
        map<ItemViewModelDemo>(BR.item, R.layout.item_demo)
    }

}
