package com.hiwitech.android.widget.recycler

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UpPullRecycylerViewOnScrollListener(
    private val onLoadMoreData: (() -> Unit)? = null,
    private val onRefreshData: (() -> Unit)? = null
) : RecyclerView.OnScrollListener() {

    /**
     * 标记是否正在向上滑动
     */
    var isUpPull = false

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val manager = recyclerView.layoutManager as LinearLayoutManager
        if (newState == RecyclerView.SCROLL_STATE_IDLE) { //总数
            val itemCount = manager.itemCount
            //最后显示的位置
            val lastItemPosition = manager.findLastCompletelyVisibleItemPosition()
            if (lastItemPosition == itemCount - 1 && isUpPull) {
                onLoadMoreData?.invoke()
            }
            //第一个显示的位置
            val fristItemPosition = manager.findFirstCompletelyVisibleItemPosition()
            if (fristItemPosition == 0 && !isUpPull) {
                onRefreshData?.invoke()
            }
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        isUpPull = dy > 0
    }
}