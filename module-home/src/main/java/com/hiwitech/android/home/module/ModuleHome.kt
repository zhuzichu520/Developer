package com.hiwitech.android.home.module

import androidx.lifecycle.ViewModel
import com.hiwitech.android.home.fragment.FragmentHome
import com.hiwitech.android.home.viewmodel.ViewModelHome
import com.hiwitech.android.mvvm.di.ChildFragmentScoped
import com.hiwitech.android.mvvm.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ModuleHome {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun fragment(): FragmentHome

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelHome::class)
    abstract fun viewModel(viewModel: ViewModelHome): ViewModel
}
