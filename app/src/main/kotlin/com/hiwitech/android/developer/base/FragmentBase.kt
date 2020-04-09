package com.hiwitech.android.developer.base

import androidx.databinding.ViewDataBinding
import com.hiwitech.android.mvvm.base.BaseArg
import com.hiwitech.android.mvvm.base.BaseFragment
import com.hiwitech.android.mvvm.base.BaseViewModel

abstract class FragmentBase<TBinding : ViewDataBinding, TViewModel : BaseViewModel<TArg>, TArg : BaseArg> :
    BaseFragment<TBinding, TViewModel, TArg>()
