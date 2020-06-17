package com.hiwitech.android.developer.ui.demo.notify.module

import androidx.lifecycle.ViewModel
import com.hiwitech.android.developer.ui.demo.notify.fragment.FragmentNotify
import com.hiwitech.android.developer.ui.demo.notify.viewmodel.ViewModelNotify
import com.hiwitech.android.mvvm.di.ChildFragmentScoped
import com.hiwitech.android.mvvm.di.ViewModelKey
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
internal abstract class ModuleNotify {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun fragment(): FragmentNotify

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelNotify::class)
    abstract fun viewModel(viewModel: ViewModelNotify): ViewModel
}
