package com.chuzi.android.shared.databinding.view

import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEachIndexed
import androidx.databinding.BindingAdapter
import com.jakewharton.rxbinding4.view.clicks
import com.chuzi.android.mvvm.databinding.BindingCommand
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

/**
 * 控制点击时间
 */
private fun <T> Observable<T>.isThrottleFirst(
    isThrottleFirst: Boolean
): Observable<T> {
    return this.compose {
        if (isThrottleFirst) {
            it.throttleFirst(150L, TimeUnit.MILLISECONDS)
        } else {
            it
        }
    }
}

@BindingAdapter(value = ["onClickCommand", "isThrottleFirst"], requireAll = false)
fun onClickCommand(view: View, clickCommand: BindingCommand<*>?, isThrottleFirst: Boolean?) {
    clickCommand?.apply {
        view.clicks().isThrottleFirst(isThrottleFirst ?: true).subscribe {
            execute()
        }
    }
}

@BindingAdapter(value = ["displayedChild"], requireAll = false)
fun bindViewGroup(viewGroup: ViewGroup, position: Int?) {
    viewGroup.forEachIndexed { index, view ->
        if (position == index) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}

@BindingAdapter(value = ["visibility"], requireAll = false)
fun bindViewVisibility(view: View, visibility: Int) {
    view.visibility = visibility
}

@BindingAdapter(value = ["currentView"], requireAll = false)
fun replyCurrentView(currentView: View, bindingCommand: BindingCommand<*>?) {
    bindingCommand?.execute(currentView)
}


/**
 * 设置控件Click监听事件
 *
 * @param onClickListener 监听实例
 * @param views           视图集合
 */
fun setOnClickDoubleListener(
    onClickListener: View.OnClickListener?,
    vararg views: View?,
    isThrottleFirst: Boolean? = null
) {
    if (views.isNotEmpty() && onClickListener != null) {
        for (view in views) {
            view?.let { v ->
                v.clicks().isThrottleFirst(isThrottleFirst ?: true).subscribe {
                    onClickListener.onClick(v)
                }
            }
        }
    }
}
