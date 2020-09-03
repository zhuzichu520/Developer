package com.hiwitech.android.detail.module

import androidx.lifecycle.ViewModel
import com.hiwitech.android.detail.dialog.BottomDialogFragmentDetail
import com.hiwitech.android.detail.dialog.DialogFragmentDetail
import com.hiwitech.android.detail.fragment.FragmentDetail
import com.hiwitech.android.detail.viewmodel.ViewModelDetail
import com.hiwitech.android.mvvm.di.DialogFragmentScoped
import com.hiwitech.android.mvvm.di.FragmentScoped
import com.hiwitech.android.mvvm.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ModuleDetail {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun fragment(): FragmentDetail

    @DialogFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun dialogFragment(): DialogFragmentDetail

    @DialogFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun dialogBottomFragment(): BottomDialogFragmentDetail

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelDetail::class)
    abstract fun viewModel(viewModel: ViewModelDetail): ViewModel
}