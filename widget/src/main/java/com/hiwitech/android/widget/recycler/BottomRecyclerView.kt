package com.hiwitech.android.widget.recycler

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class BottomRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var scrollListener: RecyclerViewScrollListener? = null
    private var onScrollBottomListener: OnScrollBottomListener? = null

    override fun setLayoutManager(layout: LayoutManager?) {
        super.setLayoutManager(layout)
        if (scrollListener == null && layout != null) {
            scrollListener = object : RecyclerViewScrollListener(layout) {
                override fun onScrollBottom(view: RecyclerView?, totalItemsCount: Int) {
                    onScrollBottomListener?.onScrollBottom()
                }
            }
        }
        scrollListener?.let { addOnScrollListener(it) }
    }

    fun setOnScrollBottomListener(onScrollBottomListener: OnScrollBottomListener?) {
        this.onScrollBottomListener = onScrollBottomListener
    }
}