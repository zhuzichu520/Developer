package com.hiwitech.android.developer.ui.main.module

import androidx.lifecycle.ViewModel
import com.hiwitech.android.developer.ui.main.fragment.FragmentMain
import com.hiwitech.android.developer.ui.main.viewmodel.ViewModelMain
import com.hiwitech.android.mvvm.di.FragmentScoped
import com.hiwitech.android.mvvm.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class ModuleMain {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun fragment(): FragmentMain

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelMain::class)
    abstract fun viewModel(viewModel: ViewModelMain): ViewModel
}
