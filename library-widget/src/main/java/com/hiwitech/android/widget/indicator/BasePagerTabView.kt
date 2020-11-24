package com.hiwitech.android.widget.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout

abstract class BasePagerTabView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), IPagerTabView {

    init {
        gravity = Gravity.CENTER
        val itemTabView = this.onCreateTabView(context)
        this.addView(itemTabView)
    }
}
