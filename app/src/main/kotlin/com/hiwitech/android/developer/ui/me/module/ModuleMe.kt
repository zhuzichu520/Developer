package com.hiwitech.android.developer.ui.me.module

import androidx.lifecycle.ViewModel
import com.hiwitech.android.developer.ui.me.fragment.FragmentMe
import com.hiwitech.android.developer.ui.me.viewmodel.ViewModelMe
import com.hiwitech.android.mvvm.di.ChildFragmentScoped
import com.hiwitech.android.mvvm.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class ModuleMe {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun fragment(): FragmentMe

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelMe::class)
    abstract fun viewModel(viewModel: ViewModelMe): ViewModel
}
