package com.hiwitech.android.mvvm.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

/**
 * desc ViewModelModule
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}