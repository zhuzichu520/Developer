package com.chuzi.android.widget.spanly.spans

import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

/**
 * desc
 * author: 朱子楚
 * time: 2020/7/1 4:52 PM
 * since: v 1.0.0
 */
class FontSpan(
    private val font: Typeface
) : MetricAffectingSpan() {

    override fun updateMeasureState(textPaint: TextPaint) = update(textPaint)

    override fun updateDrawState(textPaint: TextPaint) = update(textPaint)

    private fun update(textPaint: TextPaint) {
        textPaint.apply {
            val old = typeface
            val oldStyle = old?.style ?: 0

            // Keep the style set before
            val font = Typeface.create(font, oldStyle)
            typeface = font
        }
    }
}