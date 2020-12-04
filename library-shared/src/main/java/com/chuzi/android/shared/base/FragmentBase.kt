package com.chuzi.android.shared.base

import androidx.databinding.ViewDataBinding
import com.chuzi.android.mvvm.base.BaseArg
import com.chuzi.android.mvvm.base.BaseFragment
import com.chuzi.android.mvvm.base.BaseViewModel

abstract class FragmentBase<TBinding : ViewDataBinding, TViewModel : BaseViewModel<TArg>, TArg : BaseArg> :
    BaseFragment<TBinding, TViewModel, TArg>()