package com.chuzi.android.widget.recycler.decoration

import android.graphics.Canvas
import android.graphics.Rect
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleGridDecorationHelper(
    private val mBuilder: SuperOffsetDecoration.Builder,
    private val mLayoutManager: LinearLayoutManager
) : OrientationDecorationHelper {
    private val spanCount = (mLayoutManager as GridLayoutManager).spanCount
    private val spanSizeLookup = (mLayoutManager as GridLayoutManager).spanSizeLookup
    private val mEachSpace =
        (mBuilder.mCrossAxisEdgeSpace * 2 + mBuilder.mCrossAxisSpace * (spanCount - 1)) / spanCount

    init {
        spanSizeLookup.isSpanIndexCacheEnabled = true

    }

    override fun setOffset(outRect: Rect, childAdapterPosition: Int, itemCount: Int) {
        val diff =
            ((mEachSpace - mBuilder.mCrossAxisEdgeSpace) - mBuilder.mCrossAxisEdgeSpace) / (spanCount - 1)
        val column = spanSizeLookup.getSpanIndex(childAdapterPosition, spanCount)
        val leftOrTop =
            (column + 1 - 1) * diff + mBuilder.mCrossAxisEdgeSpace
        val rightOrBottom = mEachSpace - leftOrTop
        val totalGroup = spanSizeLookup.getSpanGroupIndex(itemCount - 1, spanCount)
        val currentGroup = spanSizeLookup.getSpanGroupIndex(childAdapterPosition, spanCount)
        val isSingleSpan = spanCount == spanSizeLookup.getSpanSize(childAdapterPosition)
        if (mLayoutManager.orientation == RecyclerView.VERTICAL) {
            if (totalGroup == 1 && currentGroup == totalGroup) {
                outRect.set(
                    leftOrTop,
                    mBuilder.mMainAxisEdgeSpace,
                    rightOrBottom,
                    mBuilder.mMainAxisEdgeSpace
                )
            } else {
                when (currentGroup) {
                    0 ->
                        outRect.set(
                            if (isSingleSpan) mBuilder.mCrossAxisEdgeSpace else leftOrTop,
                            if (mLayoutManager.reverseLayout) mBuilder.mMainAxisSpace else mBuilder.mMainAxisEdgeSpace,
                            if (isSingleSpan) mBuilder.mCrossAxisEdgeSpace else rightOrBottom,
                            if (mLayoutManager.reverseLayout) mBuilder.mMainAxisEdgeSpace else mBuilder.mMainAxisSpace
                        )
                    totalGroup ->
                        outRect.set(
                            if (isSingleSpan) mBuilder.mCrossAxisEdgeSpace else leftOrTop,
                            if (mLayoutManager.reverseLayout) mBuilder.mMainAxisEdgeSpace else 0,
                            if (isSingleSpan) mBuilder.mCrossAxisEdgeSpace else rightOrBottom,
                            if (mLayoutManager.reverseLayout) 0 else mBuilder.mMainAxisEdgeSpace
                        )
                    else ->
                        outRect.set(
                            if (isSingleSpan) mBuilder.mCrossAxisEdgeSpace else leftOrTop,
                            if (mLayoutManager.reverseLayout) mBuilder.mMainAxisSpace else 0,
                            if (isSingleSpan) mBuilder.mCrossAxisEdgeSpace else rightOrBottom,
                            if (mLayoutManager.reverseLayout) 0 else mBuilder.mMainAxisSpace
                        )
                }
            }
        } else if (mLayoutManager.orientation == RecyclerView.HORIZONTAL) {
            if (totalGroup == 1 && currentGroup == totalGroup) {
                outRect.set(
                    leftOrTop,
                    mBuilder.mMainAxisEdgeSpace,
                    rightOrBottom,
                    mBuilder.mMainAxisEdgeSpace
                )
            } else {
                when (currentGroup) {
                    0 ->
                        outRect.set(
                            if (mLayoutManager.reverseLayout) mBuilder.mMainAxisSpace else mBuilder.mMainAxisEdgeSpace,
                            if (isSingleSpan) mBuilder.mCrossAxisEdgeSpace else leftOrTop,
                            if (mLayoutManager.reverseLayout) mBuilder.mMainAxisEdgeSpace else mBuilder.mMainAxisSpace,
                            if (isSingleSpan) mBuilder.mCrossAxisEdgeSpace else rightOrBottom
                        )
                    totalGroup ->
                        outRect.set(
                            if (mLayoutManager.reverseLayout) mBuilder.mMainAxisEdgeSpace else 0,
                            if (isSingleSpan) mBuilder.mCrossAxisEdgeSpace else leftOrTop,
                            if (mLayoutManager.reverseLayout) 0 else mBuilder.mMainAxisEdgeSpace,
                            if (isSingleSpan) mBuilder.mCrossAxisEdgeSpace else rightOrBottom
                        )
                    else -> outRect.set(
                        if (mLayoutManager.reverseLayout) mBuilder.mMainAxisSpace else 0,
                        if (isSingleSpan) mBuilder.mCrossAxisEdgeSpace else leftOrTop,
                        if (mLayoutManager.reverseLayout) 0 else mBuilder.mMainAxisSpace,
                        if (isSingleSpan) mBuilder.mCrossAxisEdgeSpace else rightOrBottom
                    )
                }
            }
        }
    }

    override fun drawDivide(
        c: Canvas,
        builder: SuperOffsetDecoration.Builder,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

    }
}