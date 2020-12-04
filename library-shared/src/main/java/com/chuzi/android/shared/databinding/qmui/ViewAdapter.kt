package com.chuzi.android.shared.databinding.qmui

import androidx.databinding.BindingAdapter
import com.qmuiteam.qmui.widget.QMUITopBarLayout

@BindingAdapter(value = ["qmuiTopTitle"], requireAll = false)
fun bindQMUITopBarLayout(
        qmuiTopBarLayout: QMUITopBarLayout,
        title: String?
) {
    title?.let {
        qmuiTopBarLayout.setTitle(it)
    }
}