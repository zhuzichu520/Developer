package com.chuzi.android.widget.spanly

import android.text.SpannableStringBuilder
import com.chuzi.android.widget.spanly.helpers.span

/**
 * desc
 * author: 朱子楚
 * time: 2020/7/1 4:51 PM
 * since: v 1.0.0
 */
class Spanly : SpannableStringBuilder() {

    override fun append(text: CharSequence): Spanly {
        super.append(text)
        return this
    }

    fun append(text: CharSequence, vararg spans: Any): Spanly {
        var newText = text
        spans.forEach { item ->
            newText = span(newText, item)
        }
        super.append(newText)
        return this
    }

    fun space(): Spanly {
        super.append(" ")
        return this
    }

    fun next(): Spanly {
        super.append("\n")
        return this
    }

}