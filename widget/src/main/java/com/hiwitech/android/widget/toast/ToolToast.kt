package com.hiwitech.android.widget.toast

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import com.hiwitech.android.libs.tool.dp2px
import com.hiwitech.android.widget.R

fun toast(context: Context, @StringRes id: Int): Toast {
    return makeText(context, id).apply {
        show()
    }
}

fun toast(context: Context, @StringRes id: Int, gravity: Int): Toast {
    return makeText(context, id, gravity).apply {
        show()
    }
}

fun toast(context: Context, message: String): Toast {
    return makeText(context, message).apply {
        show()
    }
}

fun toast(context: Context, message: String, gravity: Int): Toast {
    return makeText(context, message, gravity).apply {
        show()
    }
}

fun Int.toast(context: Context): Toast {
    return makeText(context, this).apply {
        show()
    }
}

fun Int.toast(context: Context, gravity: Int): Toast {
    return makeText(context, this, gravity).apply {
        show()
    }
}

fun String?.toast(context: Context): Toast {
    return makeText(context, this).apply {
        show()
    }
}

fun String?.toast(context: Context, gravity: Int): Toast {
    return makeText(context, this, gravity).apply {
        show()
    }
}

@SuppressLint("InflateParams")
private fun makeText(
    @NonNull context: Context,
    @NonNull id: Int
): Toast {
    return makeText(context, id, Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
}

@SuppressLint("InflateParams")
private fun makeText(
    @NonNull context: Context,
    @NonNull id: Int,
    @NonNull gravity: Int
): Toast {
    val result = Toast(context)
    val inflate = LayoutInflater.from(context)
    val tv = inflate.inflate(R.layout.layout_toast, null) as TextView
    tv.setText(id)
    result.view = tv
    result.setGravity(gravity, 0, dp2px(context,30f))
    result.duration = Toast.LENGTH_SHORT
    return result
}

@SuppressLint("InflateParams")
private fun makeText(
    @NonNull context: Context,
    @NonNull text: CharSequence?
): Toast {
    return makeText(context, text, Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
}

@SuppressLint("InflateParams")
private fun makeText(
    @NonNull context: Context,
    @NonNull text: CharSequence?,
    @NonNull gravity: Int
): Toast {
    val result = Toast(context)
    val inflate = LayoutInflater.from(context)
    val tv = inflate.inflate(R.layout.layout_toast, null) as TextView
    tv.text = text
    result.view = tv
    result.setGravity(gravity, 0, dp2px(context,30f))
    result.duration = Toast.LENGTH_SHORT
    return result
}