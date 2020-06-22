package com.hiwitech.android.developer.ui.easyfloat.module

import androidx.lifecycle.ViewModel
import com.hiwitech.android.developer.ui.easyfloat.fragment.FragmentFloat
import com.hiwitech.android.developer.ui.easyfloat.viewmodel.ViewModelFloat
import com.hiwitech.android.mvvm.di.FragmentScoped
import com.hiwitech.android.mvvm.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * desc
 * author: 朱子楚
 * time: 2020/6/17 2:26 PM
 * since: v 1.0.0
 */

@Module
internal abstract class ModuleFloat {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun fragment(): FragmentFloat

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelFloat::class)
    abstract fun viewModel(viewModel: ViewModelFloat): ViewModel

}
