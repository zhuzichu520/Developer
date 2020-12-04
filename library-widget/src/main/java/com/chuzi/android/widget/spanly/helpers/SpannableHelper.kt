package com.chuzi.android.widget.spanly.helpers

import android.text.Spannable
import android.text.SpannableString

/**
 * desc
 * author: 朱子楚
 * time: 2020/7/1 4:54 PM
 * since: v 1.0.0
 */
internal fun span(charSequence: CharSequence, o: Any) =
    (if (charSequence is String) SpannableString(charSequence) else charSequence as? SpannableString
        ?: SpannableString(
            ""
        )).apply {
        setSpan(
            o,
            0,
            length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }