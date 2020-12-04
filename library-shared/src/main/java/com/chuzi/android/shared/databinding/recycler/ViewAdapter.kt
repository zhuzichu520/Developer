package com.chuzi.android.shared.databinding.recycler

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chuzi.android.libs.tool.dp2px
import com.chuzi.android.mvvm.databinding.BindingCommand
import com.chuzi.android.shared.R
import com.chuzi.android.shared.ext.toColorByResId
import com.chuzi.android.widget.recycler.decoration.SuperOffsetDecoration

@BindingAdapter(value = ["onSwipeRefreshCommand"], requireAll = false)
fun bindSwipeRefreshLayout(
    swipeRefreshLayout: SwipeRefreshLayout,
    onSwipeRefreshCommand: BindingCommand<SwipeRefreshLayout>?
) {
    swipeRefreshLayout.setOnRefreshListener {
        onSwipeRefreshCommand?.execute(swipeRefreshLayout)
    }
}

@BindingAdapter(
    value = ["linePaddingLeft", "linePaddingRight", "lineDividerColor", "lineShowDivider", "mainAxisSpace", "crossAxisSpace"],
    requireAll = false
)
fun setLineManager(
    recyclerView: RecyclerView,
    linePaddingLeft: Float?,
    linePaddingRight: Float?,
    lineDividerColor: Int?,
    lineShowDivider: Int?,
    mainAxisSpace: Float?,
    crossAxisSpace: Float?
) {
    val context = recyclerView.context
    val paddingLeft = linePaddingLeft ?: 0F
    val paddingRight = linePaddingRight ?: 0F
    val dividerColor = lineDividerColor ?: R.color.color_999999.toColorByResId()
    val showDivider = lineShowDivider ?: SuperOffsetDecoration.SHOW_DIVIDER_MIDDLE
    val mainspace = mainAxisSpace ?: 0.75f
    val crossSpace = crossAxisSpace ?: 0.75f

    val layoutManager = recyclerView.layoutManager
    if (layoutManager is LinearLayoutManager) {
        val decoration = SuperOffsetDecoration.Builder(layoutManager, recyclerView.context)
            .setPaddingLeft(dp2px(context, paddingLeft))
            .setPaddingRight(dp2px(context, paddingRight))
            .setDividerColor(dividerColor)
            .setShowDividers(showDivider)
            .setMainAxisSpace(dp2px(context, mainspace))
            .setCrossAxisSpace(dp2px(context, crossSpace))
            .build()
        recyclerView.addItemDecoration(decoration)
    }
}