package com.hiwitech.android.route

import androidx.lifecycle.ViewModel
import com.hiwitech.android.mvvm.di.ChildFragmentScoped
import com.hiwitech.android.mvvm.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ModuleRoute {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun fragment(): FragmentRoute

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelRoute::class)
    abstract fun viewModel(viewModel: ViewModelRoute): ViewModel

}
