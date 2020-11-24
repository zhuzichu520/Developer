package com.hiwitech.android.widget.indicator

import android.content.Context
import android.view.View
import android.widget.TextView

interface IPagerTabView {

    fun getTabTextView(): TextView?

    fun onCreateTabView(context: Context): View
}
