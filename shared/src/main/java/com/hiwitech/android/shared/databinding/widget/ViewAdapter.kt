package com.hiwitech.android.shared.databinding.widget

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.hiwitech.android.mvvm.databinding.BindingCommand
import com.hiwitech.android.widget.toolbar.NiceToolbar

@BindingAdapter(
    value = [
        "toolbarTitle",
        "toolbarLeftText",
        "toolbarLeftIcon",
        "toolbarRightText",
        "toolbarRightIcon",
        "onClickStartCommand",
        "onClickEndCommand"
    ],
    requireAll = false
)
fun bindToolbar(
    toolbar: NiceToolbar,
    toolbarTitle: CharSequence?,
    toolbarStartText: CharSequence?,
    toolbarStartIcon: Int?,
    toolbarEndText: CharSequence?,
    toolbarEndIcon: Int?,
    onClickStartCommand: BindingCommand<*>?,
    onClickEndCommand: BindingCommand<*>?
) {
    toolbarTitle?.let {
        toolbar.titleText = it.toString()
    }
    toolbarStartText?.let {
        toolbar.startText= it.toString()
    }
    toolbarStartIcon?.let {
        toolbar.startIcon = it
    }
    toolbarEndText?.let {
        toolbar.endText = it.toString()
    }
    toolbarEndIcon?.let {
        toolbar.endIcon = it
    }
    toolbar.onClickStartListener = View.OnClickListener {
        onClickStartCommand?.execute()
    }
    toolbar.onClickEndListener = View.OnClickListener {
        onClickEndCommand?.execute()
    }
}

@BindingAdapter(value = ["onSearchChange", "onSearchSubmit"], requireAll = false)
fun bindSearchView(
    searchView: SearchView,
    onSearchChange: BindingCommand<*>?,
    onSearchSubmit: BindingCommand<*>?
) {
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            onSearchSubmit?.execute(query)
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            onSearchChange?.execute(newText)
            return true
        }
    })
}

@BindingAdapter("onCloaseCommand")
fun onCloaseCommand(chip: Chip, onCloaseCommand: BindingCommand<Any>? = null) {
    chip.setOnCloseIconClickListener {
        onCloaseCommand?.execute()
    }
}