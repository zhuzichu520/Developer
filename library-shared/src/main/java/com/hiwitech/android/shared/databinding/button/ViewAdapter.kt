package com.hiwitech.android.shared.databinding.button

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.button.MaterialButtonToggleGroup

@BindingAdapter("checkedButton")
fun checkedButton(toggleGroup: MaterialButtonToggleGroup, id: Int?) {
    id?.let {
        toggleGroup.check(id)
    }
}

@InverseBindingAdapter(attribute = "checkedButton", event = "onCheckedButtonListener")
fun getCheckedButton(toggleGroup: MaterialButtonToggleGroup): Int {
    return toggleGroup.checkedButtonId
}

@BindingAdapter("onCheckedButtonListener")
fun setOnCheckedButtonListener(
    toggleGroup: MaterialButtonToggleGroup,
    bindingListener: InverseBindingListener?
) {
    bindingListener?.let {
        toggleGroup.addOnButtonCheckedListener { _, _, isChecked ->
            if (isChecked)
                bindingListener.onChange()
        }
    }
}