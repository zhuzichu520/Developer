package com.chuzi.android.shared.databinding.widget

import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.chuzi.android.mvvm.databinding.BindingCommand

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