package com.hiwitech.android.navigation.module

import androidx.lifecycle.ViewModel
import com.hiwitech.android.mvvm.di.ChildFragmentScoped
import com.hiwitech.android.mvvm.di.ViewModelKey
import com.hiwitech.android.navigation.fragment.FragmentNavigation
import com.hiwitech.android.navigation.viewmodel.ViewModelNavigation
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ModuleNavigation {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun fragment(): FragmentNavigation

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelNavigation::class)
    abstract fun viewModel(viewModel: ViewModelNavigation): ViewModel
}
