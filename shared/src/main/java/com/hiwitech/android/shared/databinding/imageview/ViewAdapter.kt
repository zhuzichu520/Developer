package com.hiwitech.android.shared.databinding.imageview

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.hiwitech.android.shared.glide.GlideApp

@BindingAdapter(value = ["url", "isCircle", "placeholder", "error"], requireAll = false)
fun bindSimpleDraweeView(
    imageView: ImageView,
    url: Any?,
    isCircle: Boolean?,
    placeholder: Int?,
    error: Int?
) {
    url?.apply {
        val requestOptions = RequestOptions()
        val glide = GlideApp.with(imageView).load(url)
        if (true == isCircle) {
            requestOptions.circleCrop().autoClone()
        }
        placeholder?.let {
            glide.thumbnail(GlideApp.with(imageView).load(placeholder).apply(requestOptions))
        }
        error?.let {
            glide.thumbnail(GlideApp.with(imageView).load(error).apply(requestOptions))
        }
        glide.apply(requestOptions)
        glide.into(imageView)
    }
}

@BindingAdapter(value = ["srcColor"], requireAll = false)
fun bindImageViewSrcColor(
    imageView: ImageView,
    @ColorInt color: Int?
) {
    color?.let {
        imageView.setColorFilter(it)
    }
}

@BindingAdapter(value = ["srcBitmap"], requireAll = false)
fun bindImageViewSrcBitmap(
    imageView: ImageView,
    bitmap: Bitmap?
) {
    bitmap?.let {
        imageView.setImageBitmap(it)
    }
}

@BindingAdapter(value = ["srcDrawable"], requireAll = false)
fun bindImageViewSrcDrawable(
    imageView: ImageView,
    drawable: Drawable?
) {
    drawable?.let {
        imageView.setImageDrawable(it)
    }
}