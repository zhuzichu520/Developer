package com.chuzi.android.shared.base

import androidx.databinding.ViewDataBinding
import com.chuzi.android.mvvm.base.BaseArg
import com.chuzi.android.mvvm.base.BaseDialogFragment
import com.chuzi.android.mvvm.base.BaseViewModel

abstract class DialogFragmentBase<TBinding : ViewDataBinding, TViewModel : BaseViewModel<TArg>, TArg : BaseArg> :
    BaseDialogFragment<TBinding, TViewModel, TArg>()
