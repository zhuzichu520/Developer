package com.hiwitech.android.mvvm.databinding

import com.hiwitech.android.libs.tool.toCast

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