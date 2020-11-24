package com.hiwitech.android.widget.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

class PagerTabView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BasePagerTabView(context, attrs, defStyleAttr) {
    private var mTextView: TextView? = null

    override fun getTabTextView(): TextView? {
        return mTextView
    }

    override fun onCreateTabView(context: Context): View {
        mTextView = TextView(context)
        return mTextView!!
    }

}
