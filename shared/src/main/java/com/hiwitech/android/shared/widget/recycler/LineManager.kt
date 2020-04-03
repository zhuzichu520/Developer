package com.hiwitech.android.shared.widget.recycler

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class LineManager {

    interface Factory {
        fun create(recyclerView: RecyclerView): ItemDecoration
    }

    companion object {

        fun both(dividerSize: Int): Factory {
            return object : Factory {
                override fun create(recyclerView: RecyclerView): ItemDecoration {
                    return DividerLine(
                        recyclerView.context,
                        dividerSize,
                        DividerLine.LineDrawMode.BOTH
                    )
                }
            }
        }

        fun horizontal(dividerSize: Int): Factory {
            return object : Factory {
                override fun create(recyclerView: RecyclerView): ItemDecoration {
                    return DividerLine(
                        recyclerView.context,
                        dividerSize,
                        DividerLine.LineDrawMode.HORIZONTAL
                    )
                }
            }
        }

        fun vertical(dividerSize: Int): Factory {
            return object : Factory {
                override fun create(recyclerView: RecyclerView): ItemDecoration {
                    return DividerLine(
                        recyclerView.context,
                        dividerSize,
                        DividerLine.LineDrawMode.VERTICAL
                    )
                }
            }
        }

        fun gridSpacing(spanCount: Int, spacing: Int): Factory {
            return object : Factory {
                override fun create(recyclerView: RecyclerView): ItemDecoration {
                    return GridSpacingDecoration(
                        spanCount,
                        spacing,
                        false
                    )
                }
            }
        }

    }
}