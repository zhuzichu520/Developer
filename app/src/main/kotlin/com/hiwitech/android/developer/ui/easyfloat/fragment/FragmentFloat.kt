package com.hiwitech.android.developer.ui.easyfloat.fragment

import android.app.AlertDialog
import android.view.Gravity
import android.view.MotionEvent
import android.widget.*
import com.hiwitech.android.developer.R
import com.hiwitech.android.developer.BR
import com.hiwitech.android.developer.base.FragmentBase
import com.hiwitech.android.developer.databinding.FragmentFloatBinding
import com.hiwitech.android.developer.ui.easyfloat.viewmodel.ViewModelFloat
import com.hiwitech.android.developer.ui.widget.RoundProgressBar
import com.hiwitech.android.developer.ui.widget.ScaleImage
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.shared.ext.toast
import com.hiwitech.android.widget.toast.toast
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.enums.ShowPattern
import com.lzf.easyfloat.enums.SidePattern
import com.lzf.easyfloat.interfaces.OnInvokeView
import com.lzf.easyfloat.permission.PermissionUtils
import kotlinx.android.synthetic.main.fragment_float.*
import java.lang.Integer.max

/**
 * desc
 * author: 朱子楚
 * time: 2020/6/17 2:26 PM
 * since: v 1.0.0
 */

class FragmentFloat : FragmentBase<FragmentFloatBinding, ViewModelFloat, ArgDefault>() {

    override fun setLayoutId(): Int = R.layout.fragment_float

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        super.initView()
        open.setOnClickListener {
            checkPermission()
        }
    }


    private fun showAppFloat() {
        EasyFloat.with(requireActivity())
            .setShowPattern(ShowPattern.ALL_TIME)
            .setSidePattern(SidePattern.RESULT_SIDE)
            .setGravity(Gravity.CENTER)
            .setLayout(R.layout.float_app, OnInvokeView {
                it.findViewById<ImageView>(R.id.ivClose).setOnClickListener {
                    EasyFloat.dismissAppFloat()
                }
                it.findViewById<TextView>(R.id.tvOpenMain).setOnClickListener {

                }
                it.findViewById<CheckBox>(R.id.checkbox)
                    .setOnCheckedChangeListener { _, isChecked ->
                        EasyFloat.appFloatDragEnable(isChecked)
                    }

                val progressBar = it.findViewById<RoundProgressBar>(R.id.roundProgressBar).apply {
                    setProgress(66, "66")
                    setOnClickListener { getProgressStr().toast()}
                }
                it.findViewById<SeekBar>(R.id.seekBar)
                    .setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(
                            seekBar: SeekBar?, progress: Int, fromUser: Boolean
                        ) = progressBar.setProgress(progress, progress.toString())

                        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                    })

//                // 解决 ListView 拖拽滑动冲突
//                it.findViewById<ListView>(R.id.lv_test).apply {
//                    adapter = MyAdapter(
//                        this@MainActivity,
//                        arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "...")
//                    )
//
//                    // 监听 ListView 的触摸事件，手指触摸时关闭拖拽，手指离开重新开启拖拽
//                    setOnTouchListener { _, event ->
//                        logger.e("listView: ${event.action}")
//                        EasyFloat.appFloatDragEnable(event?.action == MotionEvent.ACTION_UP)
//                        false
//                    }
//                }
            })
            .show()
    }

    private fun checkPermission(tag: String? = null) {
        if (PermissionUtils.checkPermission(requireContext())) {
            if (tag == null) showAppFloat() else showAppFloat2(tag)
        } else {
            AlertDialog.Builder(requireContext())
                .setMessage("使用浮窗功能，需要您授权悬浮窗权限。")
                .setPositiveButton("去开启") { _, _ ->
                    if (tag == null) showAppFloat() else showAppFloat2(tag)
                }
                .setNegativeButton("取消") { _, _ -> }
                .show()
        }
    }

    private fun showAppFloat2(tag: String) {
        EasyFloat.with(requireActivity())
            .setTag(tag)
            .setShowPattern(ShowPattern.BACKGROUND)
            .setLocation(100, 100)
            .setAppFloatAnimator(null)
            .setLayout(R.layout.float_app_scale, OnInvokeView {
                val content = it.findViewById<RelativeLayout>(R.id.rlContent)
                val params = content.layoutParams as FrameLayout.LayoutParams
                it.findViewById<ScaleImage>(R.id.ivScale).onScaledListener =
                    object : ScaleImage.OnScaledListener {
                        override fun onScaled(x: Float, y: Float, event: MotionEvent) {
                            params.width = max(params.width + x.toInt(), 100)
                            params.height = max(params.height + y.toInt(), 100)
                            content.layoutParams = params
                        }
                    }

                it.findViewById<ImageView>(R.id.ivClose).setOnClickListener {
                    EasyFloat.dismissAppFloat(tag)
                }
            })
            .show()
    }

}