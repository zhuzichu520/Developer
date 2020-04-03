package com.hiwitech.android.shared.ext

import com.hiwitech.android.shared.log.lumberjack.L

private const val TAG = "Nice"

fun Any.logi(tag: String = TAG, throwable: Throwable? = null) {
    throwable?.let {
        L.tag(tag).i(throwable) { this.toString() }
    } ?: let {
        L.tag(tag).i { this.toString() }
    }
}

fun Any.logd(tag: String = TAG, throwable: Throwable? = null) {
    throwable?.let {
        L.tag(tag).d(throwable) { this.toString() }
    } ?: let {
        L.tag(tag).d { this.toString() }
    }
}

fun Any.loge(tag: String = TAG, throwable: Throwable? = null) {
    throwable?.let {
        L.tag(tag).e(throwable) { this.toString() }
    } ?: let {
        L.tag(tag).e { this.toString() }
    }
}

fun Any.logw(tag: String = TAG, throwable: Throwable? = null) {
    throwable?.let {
        L.tag(tag).w(throwable) { this.toString() }
    } ?: let {
        L.tag(tag).w { this.toString() }
    }
}