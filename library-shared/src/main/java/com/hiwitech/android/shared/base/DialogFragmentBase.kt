package com.hiwitech.android.shared.base

import androidx.databinding.ViewDataBinding
import com.hiwitech.android.mvvm.base.BaseArg
import com.hiwitech.android.mvvm.base.BaseDialogFragment
import com.hiwitech.android.mvvm.base.BaseViewModel

abstract class DialogFragmentBase<TBinding : ViewDataBinding, TViewModel : BaseViewModel<TArg>, TArg : BaseArg> :
    BaseDialogFragment<TBinding, TViewModel, TArg>()
