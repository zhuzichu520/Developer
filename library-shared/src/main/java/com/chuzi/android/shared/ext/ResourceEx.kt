package com.chuzi.android.shared.ext

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.chuzi.android.shared.global.AppGlobal.context

fun Int.toStringByResId(): String {
    return context.getString(this)
}

fun Int.toStringByResId(context: Context): String {
    return context.getString(this)
}

fun Int.toColorByResId(): Int {
    return ContextCompat.getColor(context, this)
}

fun Int.toArrayByResId(): Array<String> {
    return context.resources.getStringArray(this)
}

fun Int.toDrawableByResId(): Drawable? {
    return ContextCompat.getDrawable(context, this)
}

fun Int.toDrawableByResId(context: Context): Drawable? {
    return ContextCompat.getDrawable(context, this)
}
