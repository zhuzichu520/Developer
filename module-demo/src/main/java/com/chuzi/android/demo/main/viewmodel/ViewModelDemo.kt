package com.chuzi.android.demo.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.chuzi.android.demo.R
import com.chuzi.android.demo.BR
import com.chuzi.android.shared.base.ViewModelBase
import com.chuzi.android.mvvm.base.ArgDefault
import com.chuzi.android.shared.ext.map
import com.chuzi.android.shared.route.RoutePath
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
