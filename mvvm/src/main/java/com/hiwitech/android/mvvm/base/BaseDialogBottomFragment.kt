package com.hiwitech.android.mvvm.base

import android.app.Dialog
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


abstract class BaseDialogBottomFragment<TBinding : ViewDataBinding, TViewModel : BaseViewModel<TArg>, TArg : BaseArg> :
    BaseDialogFragment<TBinding, TViewModel, TArg>(), IBaseView<TArg>, IBaseCommon {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme)
    }

}