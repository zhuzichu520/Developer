package com.hiwitech.android.widget.dialog.loading

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import com.hiwitech.android.widget.R

class LoadingDialog : AppCompatDialog {

    private var layoutId: Int

    constructor(context: Context?, layout: Int) : this(context, R.style.Dialog_Loading, layout)

    constructor(context: Context?) : this(context, R.style.Dialog_Loading, R.layout.layout_loading)

    constructor(context: Context?, style: Int, layout: Int) : super(context, style) {
        val params = window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = params
        layoutId = layout
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
    }
}