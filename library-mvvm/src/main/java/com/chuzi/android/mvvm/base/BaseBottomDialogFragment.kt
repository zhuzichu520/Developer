package com.chuzi.android.mvvm.base

import android.app.Dialog
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * desc 底部动画的DilaogFragment基类
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
abstract class BaseBottomDialogFragment<TBinding : ViewDataBinding, TViewModel : BaseViewModel<TArg>, TArg : BaseArg> :
    BaseDialogFragment<TBinding, TViewModel, TArg>(), IBaseView<TArg>, IBaseCommon {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme)
    }

}