package com.hiwitech.android.widget.toolbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.TextViewCompat
import com.hiwitech.android.widget.R

class NiceToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val root: View? = LayoutInflater.from(context)
        .inflate(R.layout.layout_toolbar, this, true)

    private val titleView: TextView by lazy { findViewById<TextView>(R.id.title) }

    private val startLayout by lazy { findViewById<View>(R.id.start_layout) }
    private val startTextView: TextView by lazy { findViewById<TextView>(R.id.start_text) }
    private val startIconView: ImageView by lazy { findViewById<ImageView>(R.id.start_icon) }

    private val endLayout by lazy { findViewById<View>(R.id.end_layout) }
    private val endTextView: TextView by lazy { findViewById<TextView>(R.id.end_text) }
    private val endIconView: ImageView by lazy { findViewById<ImageView>(R.id.end_icon) }

    var titleText: String? = null
        set(value) {
            value?.let {
                field = value
                titleView.text = it
            }
        }

    var titleTextAppearance: Int? = null
        set(value) {
            value?.let {
                field = value
                TextViewCompat.setTextAppearance(titleView, it)
            }
        }

    var startText: String? = null
        set(value) {
            value?.let {
                field = value
                startTextView.text = it
                startLayout.visibility = View.VISIBLE
            }
        }

    private var startDrawable: Drawable? = null
        set(value) {
            value?.let {
                field = value
                startIconView.setImageDrawable(it)
                startLayout.visibility = View.VISIBLE
            }
        }

    var startIcon: Int? = null
        set(value) {
            value?.let {
                field = value
                startDrawable = AppCompatResources.getDrawable(context, it)
            }
        }

    var onClickStartListener: OnClickListener? = null
        set(value) {
            value?.let {
                field = value
                startLayout.setOnClickListener(it)
            }
        }

    var endText: String? = null
        set(value) {
            value?.let {
                field = value
                endTextView.text = it
                endLayout.visibility = View.VISIBLE
            }
        }

    private var endDrawable: Drawable? = null
        set(value) {
            value?.let {
                field = value
                endIconView.setImageDrawable(it)
                endLayout.visibility = View.VISIBLE
            }
        }

    var endIcon: Int? = null
        set(value) {
            value?.let {
                field = value
                endDrawable = AppCompatResources.getDrawable(context, it)
            }
        }

    var onClickEndListener: OnClickListener? = null
        set(value) {
            value?.let {
                field = value
                endLayout.setOnClickListener(it)
            }
        }

    init {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.NiceToolbar, 0, 0)

        arr.getString(R.styleable.NiceToolbar_toolbarTitle)?.let {
            titleText = it
        }

        titleTextAppearance = arr.getResourceId(
            R.styleable.NiceToolbar_toolbarTitleTextAppearance,
            R.attr.titleTextAppearance
        )

        arr.getString(R.styleable.NiceToolbar_toolbarStartText)?.let {
            startText = it
        }
        arr.getDrawable(R.styleable.NiceToolbar_toolbarStartIcon)?.let {
            startDrawable = it
        }

        arr.getString(R.styleable.NiceToolbar_toolbarEndText)?.let {
            endText = it
        }
        arr.getDrawable(R.styleable.NiceToolbar_toolbarEndIcon)?.let {
            endDrawable = it
        }

        arr.recycle()
    }
}