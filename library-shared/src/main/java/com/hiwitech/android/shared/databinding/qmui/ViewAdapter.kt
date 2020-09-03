package com.hiwitech.android.shared.databinding.qmui

import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.qmuiteam.qmui.widget.QMUITopBarLayout

@BindingAdapter(value = ["qmui_top_title"], requireAll = false)
fun bindQMUITopBarLayout(
    qmuiTopBarLayout: QMUITopBarLayout,
    title: String?
) {
    title?.let {
        qmuiTopBarLayout.setTitle(it)
    }
}