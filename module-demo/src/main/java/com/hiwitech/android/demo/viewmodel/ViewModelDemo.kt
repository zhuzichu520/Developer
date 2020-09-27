package com.hiwitech.android.demo.viewmodel

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
        const val TYPE_NOTIFY = 1
        const val TYPE_EASYFLOAT = 2
        const val TYPE_AROUTE = 3
    }

    private val closure: Int.() -> Unit = {
        when (this) {
            TYPE_NAVIGATION -> {
                navigate(RoutePath.FRAGMENT_DEMO,isPop = true)
            }
            TYPE_NOTIFY -> {
                navigate(RoutePath.ACTIVITY_DEMO,isPop = true)
            }
            TYPE_EASYFLOAT -> {
                navigate(RoutePath.FRAGMENT_FLOAT, isPop = true)
            }
            TYPE_AROUTE -> {
                navigate(RoutePath.ACTIVITY_AROUTE)
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
            ItemViewModelDemo(
                this@ViewModelDemo,
                TYPE_NOTIFY,
                R.string.demo_notify,
                closure
            ),
            ItemViewModelDemo(
                this@ViewModelDemo,
                TYPE_EASYFLOAT,
                R.string.demo_float,
                closure
            ),
            ItemViewModelDemo(
                this@ViewModelDemo,
                TYPE_AROUTE,
                R.string.aroute,
                closure
            )
        )
    }

    val itemBinding = OnItemBindClass<Any>().apply {
        map<ItemViewModelDemo>(BR.item, R.layout.item_demo)
    }
}
