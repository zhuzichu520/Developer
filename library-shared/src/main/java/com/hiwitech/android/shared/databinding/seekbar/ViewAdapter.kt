package com.hiwitech.android.shared.databinding.seekbar

import android.widget.SeekBar
import androidx.databinding.BindingAdapter
import com.hiwitech.android.mvvm.databinding.BindingCommand
import com.jakewharton.rxbinding4.widget.changes

@BindingAdapter(value = ["onSeekCommand"], requireAll = false)
fun bindSeekBar(seekBar: SeekBar, onSeekCommand: BindingCommand<Int>?) {
    onSeekCommand?.apply {
        seekBar.changes().subscribe {
            execute(it)
        }
    }
}