package com.hiwitech.android.developer.ui.demo.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import com.hiwitech.android.developer.BR
import com.hiwitech.android.developer.R
import com.hiwitech.android.developer.base.ViewModelBase
import com.hiwitech.android.developer.ui.aroute.ActivityAroute
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.mvvm.base.BaseViewModel
import com.hiwitech.android.shared.ext.map
import javax.inject.Inject
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass

class ViewModelDemo @Inject constructor() : ViewModelBase<ArgDefault>() {

    companion object {
        const val TYPE_NAVIGATION = 0
        const val TYPE_NOTIFY = 1
        const val TYPE_EASYFLOAT = 2
        const val TYPE_AROUTE = 3
    }

    private val closure: Int.() -> Unit = {
        when (this) {
            TYPE_NAVIGATION -> {
                start(R.id.action_fragmentMain_to_fragmentNavigation)
            }
            TYPE_NOTIFY -> {
                start(R.id.action_fragmentMain_to_fragmentNotify)
            }
            TYPE_EASYFLOAT -> {
                start(R.id.action_fragmentMain_to_fragmentFloat)
            }
            TYPE_AROUTE -> {
                ARouter.getInstance().build(ActivityAroute.ROUTE).navigation();
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
