package com.chuzi.android.mvvm.databinding

import com.chuzi.android.libs.tool.toCast

/**
 * desc 回调带结果的工具类
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
class ResponseCommand<T, R>(
    private var execute: (() -> R?)? = null,
    private var consumer: (T?.() -> R?)? = null,
    private var canExecute0: (() -> Boolean)? = null
) {
    fun execute(): R? {
        if (canExecute0()) {
            return execute?.invoke()
        }
        return null
    }

    fun execute(parameter: Any?): R? {
        if (canExecute0()) {
            consumer?.invoke(parameter?.toCast())
        }
        return null
    }

    private fun canExecute0(): Boolean {
        return canExecute0?.invoke() ?: true
    }
}