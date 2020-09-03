package com.hiwitech.android.shared.databinding.textview

import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["htmlText"], requireAll = false)
fun bindingHtmlText(textView: TextView, string: String?) {
    string?.let {
        textView.text = HtmlCompat.fromHtml(string, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}