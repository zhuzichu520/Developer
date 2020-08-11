package com.hiwitech.android.widget.spanly

import android.graphics.Typeface
import android.text.style.*
import android.view.View
import com.hiwitech.android.widget.spanly.spans.CustomClickableSpan
import com.hiwitech.android.widget.spanly.spans.FontSpan

/**
 * desc
 * author: 朱子楚
 * time: 2020/7/1 4:50 PM
 * since: v 1.0.0
 */
fun bold() = StyleSpan(Typeface.BOLD)
fun italic() = StyleSpan(Typeface.ITALIC)
fun underline() = UnderlineSpan()
fun strike() = StrikethroughSpan()
fun sup() = SuperscriptSpan()
fun sub() = SubscriptSpan()
fun background(color: Int) = BackgroundColorSpan(color)
fun color(color: Int) = ForegroundColorSpan(color)
fun size(size: Float) = RelativeSizeSpan(size)
fun font(typeface: Typeface) = FontSpan(typeface)
fun clickable(listener: View.OnClickListener, isUnderlineText: Boolean = false) =
    CustomClickableSpan(listener, isUnderlineText)