package com.chuzi.android.libs.listener

import android.app.Activity
import android.graphics.Rect
import android.view.View

/**
 * desc
 * author: 朱子楚
 * time: 2020/4/16 2:34 PM
 * since: v 1.0.0
 */
class KeyboardListener(
    activity: Activity,
    onKeyboardShow: (Int.() -> Unit)? = null,
    onKeyboardHide: (() -> Unit)? = null,
    onKeyboardChange: (Int.() -> Unit)? = null
) {
    private var virtualKeyboardHeight = 0

    private var screenHeight = 0

    private var screenHeight6 = 0

    private var rootView: View

    private var isKeyboardShow = false

    private var softKeyboardHeight = 0

    init {
        screenHeight = activity.resources.displayMetrics.heightPixels
        screenHeight6 = screenHeight / 6
        rootView = activity.window.decorView

        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            rootView.post {
                val rect = Rect()
                rootView.getWindowVisibleDisplayFrame(rect)
                val heightDifference = screenHeight - rect.bottom
                if (heightDifference < screenHeight6) {
                    virtualKeyboardHeight = heightDifference
                    //键盘是隐藏状态
                    if (isKeyboardShow) {
                        onKeyboardHide?.invoke()
                        isKeyboardShow = false
                    }
                } else {
                    val softKeyboardHeight = heightDifference - virtualKeyboardHeight
                    //显示键盘
                    if (!isKeyboardShow) {
                        onKeyboardShow?.invoke(softKeyboardHeight)
                        isKeyboardShow = true
                    } else {
                        //切换键盘高度发生变化
                        if (this.softKeyboardHeight != softKeyboardHeight) {
                            onKeyboardChange?.invoke(softKeyboardHeight)
                        }
                    }
                    this@KeyboardListener.softKeyboardHeight = softKeyboardHeight
                }
            }
        }
    }

}