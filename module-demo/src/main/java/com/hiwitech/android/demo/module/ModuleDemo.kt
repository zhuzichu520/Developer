package com.hiwitech.android.demo.module

import androidx.lifecycle.ViewModel
import com.hiwitech.android.demo.fragment.FragmentDemo
import com.hiwitech.android.demo.viewmodel.ViewModelDemo
import com.hiwitech.android.mvvm.di.ChildFragmentScoped
import com.hiwitech.android.mvvm.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ModuleDemo {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun fragment(): FragmentDemo

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelDemo::class)
    abstract fun viewModel(viewModel: ViewModelDemo): ViewModel
}
