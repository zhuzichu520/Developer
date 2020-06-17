package com.hiwitech.android.developer.ui.demo.main.module

import androidx.lifecycle.ViewModel
import com.hiwitech.android.developer.ui.demo.main.fragment.FragmentDemo
import com.hiwitech.android.developer.ui.demo.main.viewmodel.ViewModelDemo
import com.hiwitech.android.mvvm.di.ChildFragmentScoped
import com.hiwitech.android.mvvm.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class ModuleDemo {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun fragment(): FragmentDemo

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelDemo::class)
    abstract fun viewModel(viewModel: ViewModelDemo): ViewModel
}
