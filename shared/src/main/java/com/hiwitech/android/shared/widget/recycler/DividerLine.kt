package com.hiwitech.android.shared.widget.recycler

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.hiwitech.android.libs.tool.dp2px
import com.hiwitech.android.libs.tool.toFloat
import com.hiwitech.android.shared.R
import com.hiwitech.android.shared.ext.toColorByResId

class DividerLine(
    val context: Context,
    private val dividerSize: Int = 0,
    val mode: LineDrawMode = LineDrawMode.VERTICAL
) : ItemDecoration() {

    private var dividerDrawable: Drawable? = ColorDrawable(R.color.color_divider.toColorByResId())

    enum class LineDrawMode {
        HORIZONTAL, VERTICAL, BOTH
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        when (mode) {
            LineDrawMode.VERTICAL -> drawVertical(c, parent)
            LineDrawMode.HORIZONTAL -> drawHorizontal(c, parent)
            LineDrawMode.BOTH -> {
                drawHorizontal(c, parent)
                drawVertical(c, parent)
            }
        }
    }

    private fun drawVertical(
        c: Canvas,
        parent: RecyclerView
    ) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val top = child.top - params.topMargin
            val left = child.right + params.rightMargin
            val bottom = child.bottom + params.bottomMargin + dp2px(context, toFloat(dividerSize))
            val right: Int = left + dp2px(context, toFloat(dividerSize))
            dividerDrawable?.setBounds(left, top, right, bottom)
            dividerDrawable?.draw(c)
        }
    }

    private fun drawHorizontal(
        c: Canvas,
        parent: RecyclerView
    ) {
        val childCount = parent.childCount
        for (i in 0 until childCount) { //分别为每个item绘制分隔线,首先要计算出item的边缘在哪里,给分隔线定位,定界
            val child = parent.getChildAt(i)
            //RecyclerView的LayoutManager继承自ViewGroup,支持了margin
            val params =
                child.layoutParams as RecyclerView.LayoutParams
            //child的左边缘(也是分隔线的左边)
            val left = child.left - params.leftMargin
            //child的底边缘(恰好是分隔线的顶边)
            val top = child.bottom + params.topMargin
            //child的右边(也是分隔线的右边)
            val right = child.right - params.rightMargin
            //分隔线的底边所在的位置(那就是分隔线的顶边加上分隔线的高度)
            val bottom: Int = top + dp2px(context, toFloat(dividerSize))
            dividerDrawable?.setBounds(left, top, right, bottom)
            //画上去
            dividerDrawable?.draw(c)
        }
    }

}