package com.hiwitech.android.widget.spanly.spans

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

/**
 * desc
 * author: 朱子楚
 * time: 2020/7/1 4:53 PM
 * since: v 1.0.0
 */
class CustomClickableSpan(
    private val listener: View.OnClickListener,
    private val isUnderlineText: Boolean
) : ClickableSpan() {

    private lateinit var textPaint: TextPaint

    override fun onClick(widget: View) {
        listener.onClick(widget)
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.isUnderlineText = isUnderlineText
    }
}