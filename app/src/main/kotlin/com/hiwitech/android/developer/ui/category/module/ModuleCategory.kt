package com.hiwitech.android.developer.ui.category.module

import androidx.lifecycle.ViewModel
import com.hiwitech.android.mvvm.di.ChildFragmentScoped
import com.hiwitech.android.mvvm.di.ViewModelKey
import com.hiwitech.android.developer.ui.category.fragment.FragmentCategory
import com.hiwitech.android.developer.ui.category.viewmodel.ViewModelCategory
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class ModuleCategory {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun fragment(): FragmentCategory

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelCategory::class)
    abstract fun viewModel(viewModel: ViewModelCategory): ViewModel

}