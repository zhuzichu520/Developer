package com.hiwitech.android.shared.databinding.recycler

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hiwitech.android.mvvm.databinding.BindingCommand
import com.hiwitech.android.shared.widget.recycler.LineManager

@BindingAdapter(value = ["onSwipeRefreshCommand"], requireAll = false)
fun bindSwipeRefreshLayout(
    swipeRefreshLayout: SwipeRefreshLayout,
    onSwipeRefreshCommand: BindingCommand<SwipeRefreshLayout>?
) {
    swipeRefreshLayout.setOnRefreshListener {
        onSwipeRefreshCommand?.execute(swipeRefreshLayout)
    }
}

@BindingAdapter("lineManager")
fun setLineManager(
    recyclerView: RecyclerView,
    factory: LineManager.Factory
) {
    recyclerView.addItemDecoration(factory.create(recyclerView))
}
