package com.hiwitech.android.me.module

import androidx.lifecycle.ViewModel
import com.hiwitech.android.me.fragment.FragmentMe
import com.hiwitech.android.me.viewmodel.ViewModelMe
import com.hiwitech.android.mvvm.di.ChildFragmentScoped
import com.hiwitech.android.mvvm.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ModuleMe {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun fragment(): FragmentMe

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelMe::class)
    abstract fun viewModel(viewModel: ViewModelMe): ViewModel
}
