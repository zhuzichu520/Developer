package com.hiwitech.android.notify.module

import androidx.lifecycle.ViewModel
import com.hiwitech.android.mvvm.di.ChildFragmentScoped
import com.hiwitech.android.mvvm.di.ViewModelKey
import com.hiwitech.android.notify.fragment.FragmentNotify
import com.hiwitech.android.notify.viewmodel.ViewModelNotify
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * desc
 * author: 朱子楚
 * time: 2020/6/17 9:30 AM
 * since: v 1.0.0
 */
@Module
abstract class ModuleNotify {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun fragment(): FragmentNotify

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelNotify::class)
    abstract fun viewModel(viewModel: ViewModelNotify): ViewModel
}
