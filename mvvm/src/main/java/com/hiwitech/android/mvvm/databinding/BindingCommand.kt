package com.hiwitech.android.mvvm.databinding

import com.hiwitech.android.libs.tool.toCast

/**
 * desc 回调工具类
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
class BindingCommand<T>(
    private var execute: (() -> Unit)? = null,
    private var consumer: (T?.() -> Unit)? = null,
    private var canExecute0: (() -> Boolean)? = null
) {
    fun execute() {
        if (canExecute0()) {
            execute?.invoke()
        }
    }

    fun execute(parameter: Any?) {
        if (canExecute0()) {
            consumer?.invoke(parameter?.toCast())
        }
    }

    private fun canExecute0(): Boolean {
        return canExecute0?.invoke() ?: true
    }
}