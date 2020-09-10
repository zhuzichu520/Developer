package com.hiwitech.android.home.di

import com.hiwitech.android.home.ActivityMain
import com.hiwitech.android.home.module.ModuleHome
import com.hiwitech.android.mvvm.di.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * desc
 * author: 朱子楚
 * time: 2020/9/9 11:40 AM
 * since: v 1.0.0
 */
@Module
abstract class HomeBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            // fragments
            ModuleHome::class
        ]
    )
    internal abstract fun mainActivity(): ActivityMain
}