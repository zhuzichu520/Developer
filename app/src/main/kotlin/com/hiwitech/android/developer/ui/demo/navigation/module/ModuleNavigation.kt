package com.hiwitech.android.developer.ui.demo.navigation.module

import androidx.lifecycle.ViewModel
import com.hiwitech.android.developer.ui.demo.navigation.fragment.FragmentNavigation
import com.hiwitech.android.developer.ui.demo.navigation.viewmodel.ViewModelNavigation
import com.hiwitech.android.mvvm.di.ChildFragmentScoped
import com.hiwitech.android.mvvm.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class ModuleNavigation {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun fragment(): FragmentNavigation

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelNavigation::class)
    abstract fun viewModel(viewModel: ViewModelNavigation): ViewModel
}
