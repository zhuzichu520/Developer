package com.chuzi.android.widget.recycler.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleLinearDecorationHelper(
    private val mBuilder: SuperOffsetDecoration.Builder,
    private val mLayoutManager: LinearLayoutManager
) : OrientationDecorationHelper {

    override fun setOffset(outRect: Rect, childAdapterPosition: Int, itemCount: Int) {
        if (mLayoutManager.orientation == RecyclerView.VERTICAL) {
            if (itemCount == 1) {
                outRect.set(
                    mBuilder.mCrossAxisEdgeSpace,
                    mBuilder.mMainAxisEdgeSpace,
                    mBuilder.mCrossAxisEdgeSpace,
                    mBuilder.mMainAxisEdgeSpace
                )
            } else {
                when (childAdapterPosition) {
                    0 ->
                        outRect.set(
                            mBuilder.mCrossAxisEdgeSpace,
                            if (mLayoutManager.reverseLayout) mBuilder.mMainAxisSpace else mBuilder.mMainAxisEdgeSpace,
                            mBuilder.mCrossAxisEdgeSpace,
                            if (mLayoutManager.reverseLayout) mBuilder.mMainAxisEdgeSpace else mBuilder.mMainAxisSpace
                        )
                    itemCount - 1 ->
                        outRect.set(
                            mBuilder.mCrossAxisEdgeSpace,
                            if (mLayoutManager.reverseLayout) mBuilder.mMainAxisEdgeSpace else 0,
                            mBuilder.mCrossAxisEdgeSpace,
                            if (mLayoutManager.reverseLayout) 0 else mBuilder.mMainAxisEdgeSpace
                        )
                    else -> outRect.set(
                        mBuilder.mCrossAxisEdgeSpace,
                        if (mLayoutManager.reverseLayout) mBuilder.mMainAxisSpace else 0,
                        mBuilder.mCrossAxisEdgeSpace,
                        if (mLayoutManager.reverseLayout) 0 else mBuilder.mMainAxisSpace
                    )
                }
            }
        } else if (mLayoutManager.orientation == RecyclerView.HORIZONTAL) {
            if (itemCount == 1) {
                outRect.set(
                    mBuilder.mMainAxisEdgeSpace,
                    mBuilder.mCrossAxisEdgeSpace,
                    mBuilder.mMainAxisEdgeSpace,
                    mBuilder.mCrossAxisEdgeSpace
                )
            } else {
                when (childAdapterPosition) {
                    0 ->
                        outRect.set(
                            if (mLayoutManager.reverseLayout) mBuilder.mMainAxisSpace else mBuilder.mMainAxisEdgeSpace,
                            mBuilder.mCrossAxisEdgeSpace,
                            if (mLayoutManager.reverseLayout) mBuilder.mMainAxisEdgeSpace else mBuilder.mMainAxisSpace,
                            mBuilder.mCrossAxisEdgeSpace
                        )
                    itemCount - 1 ->
                        outRect.set(
                            if (mLayoutManager.reverseLayout) mBuilder.mMainAxisEdgeSpace else 0,
                            mBuilder.mCrossAxisEdgeSpace,
                            if (mLayoutManager.reverseLayout) 0 else mBuilder.mMainAxisEdgeSpace,
                            mBuilder.mCrossAxisEdgeSpace

                        )
                    else -> outRect.set(
                        if (mLayoutManager.reverseLayout) mBuilder.mMainAxisSpace else 0,
                        mBuilder.mCrossAxisEdgeSpace,
                        if (mLayoutManager.reverseLayout) 0 else mBuilder.mMainAxisSpace,
                        mBuilder.mCrossAxisEdgeSpace
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
        val childCount = parent.childCount
        val rectPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        val rectF = RectF()

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            if (needOffset(child, parent)) {
                if (mLayoutManager.orientation == RecyclerView.VERTICAL) {
                    rectF.left = 0F
                    rectF.right = parent.width.toFloat()
                    when (i) {
                        0 -> {
                            rectF.bottom = child.top.toFloat()
                            rectF.top = rectF.bottom - builder.mMainAxisSpace
                            drawBeginningDivider(
                                c,
                                rectF,
                                builder,
                                rectPaint,
                                true
                            )
                            rectF.top = child.bottom.toFloat()
                            rectF.bottom = rectF.top + builder.mMainAxisSpace
                            drawMiddleDivider(c, rectF, builder, rectPaint, true)
                        }
                        childCount - 1 -> {
                            rectF.top = child.bottom.toFloat()
                            rectF.bottom = rectF.top + builder.mMainAxisSpace
                            drawEndDivider(c, rectF, builder, rectPaint, true)
                        }
                        else -> {
                            rectF.top = child.bottom.toFloat()
                            rectF.bottom = rectF.top + builder.mMainAxisSpace
                            drawMiddleDivider(c, rectF, builder, rectPaint, true)
                        }
                    }
                } else if (mLayoutManager.orientation == RecyclerView.HORIZONTAL) {
                    rectF.top = 0F
                    rectF.bottom = parent.height.toFloat()
                    when (i) {
                        0 -> {
                            rectF.right = child.left.toFloat()
                            rectF.left = rectF.right - builder.mMainAxisSpace
                            drawBeginningDivider(
                                c,
                                rectF,
                                builder,
                                rectPaint,
                                false
                            )
                            rectF.left = child.right.toFloat()
                            rectF.right = rectF.left + builder.mMainAxisSpace
                            drawMiddleDivider(c, rectF, builder, rectPaint, false)
                        }
                        childCount - 1 -> {
                            rectF.left = child.right.toFloat()
                            rectF.right = rectF.left + builder.mMainAxisSpace
                            drawEndDivider(c, rectF, builder, rectPaint, false)
                        }
                        else -> {
                            rectF.left = child.right.toFloat()
                            rectF.right = rectF.left + builder.mMainAxisSpace
                            drawMiddleDivider(c, rectF, builder, rectPaint, false)
                        }
                    }

                }

            }
        }
    }

    private fun drawBeginningDivider(
        c: Canvas,
        rectF: RectF,
        builder: SuperOffsetDecoration.Builder,
        rectPaint: Paint,
        isVertical: Boolean
    ) {
        if (builder.mShowDividers.and(SuperOffsetDecoration.SHOW_DIVIDER_BEGINNING) != 0) {
            drawDivider(
                c,
                rectF,
                builder,
                rectPaint,
                isVertical
            )
        }
    }

    private fun drawEndDivider(
        c: Canvas,
        rectF: RectF,
        builder: SuperOffsetDecoration.Builder,
        rectPaint: Paint,
        isVertical: Boolean
    ) {
        if (builder.mShowDividers.and(SuperOffsetDecoration.SHOW_DIVIDER_END) != 0) {
            drawDivider(
                c,
                rectF,
                builder,
                rectPaint,
                isVertical
            )
        }
    }

    private fun drawMiddleDivider(
        c: Canvas,
        rectF: RectF,
        builder: SuperOffsetDecoration.Builder,
        rectPaint: Paint,
        isVertical: Boolean
    ) {
        if (builder.mShowDividers.and(SuperOffsetDecoration.SHOW_DIVIDER_MIDDLE) != 0) {
            drawDivider(
                c,
                rectF,
                builder,
                rectPaint,
                isVertical
            )
        }
    }

    private fun drawDivider(
        c: Canvas,
        rectF: RectF,
        builder: SuperOffsetDecoration.Builder,
        rectPaint: Paint,
        isVertical: Boolean
    ) {
        if (builder.paddingLeft == 0 && builder.paddingRight == 0) {
            rectPaint.color = builder.mDividerColor
            c.drawRect(rectF.left, rectF.top, rectF.right, rectF.bottom, rectPaint)
        } else {
            rectPaint.color = builder.mBackgroundColor
            c.drawRect(rectF.left, rectF.top, rectF.right, rectF.bottom, rectPaint)
            rectPaint.color = builder.mDividerColor
            c.drawRect(
                if (isVertical) rectF.left + builder.paddingLeft else rectF.left,
                if (isVertical) rectF.top else rectF.top + builder.paddingLeft,
                if (isVertical) rectF.right - builder.paddingRight else rectF.right,
                if (isVertical) rectF.bottom else rectF.bottom - builder.paddingRight,
                rectPaint
            )
        }

    }


}

