package com.hiwitech.android.developer.base

import android.os.Bundle
import com.hiwitech.android.mvvm.base.BaseActivity
import com.hiwitech.android.shared.theme.ThemeManager

abstract class ActivityBase : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.applyThemeOverlays(this)
        super.onCreate(savedInstanceState)
    }
}
