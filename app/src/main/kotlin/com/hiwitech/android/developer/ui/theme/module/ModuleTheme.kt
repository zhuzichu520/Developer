package com.hiwitech.android.developer.ui.theme.module

import androidx.lifecycle.ViewModel
import com.hiwitech.android.developer.ui.theme.fragment.DialogFragmentChooseTheme
import com.hiwitech.android.developer.ui.theme.viewmodel.ViewModelTheme
import com.hiwitech.android.mvvm.di.FragmentScoped
import com.hiwitech.android.mvvm.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class ModuleTheme {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun fragment(): DialogFragmentChooseTheme

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelTheme::class)
    abstract fun viewModel(viewModel: ViewModelTheme): ViewModel
}
