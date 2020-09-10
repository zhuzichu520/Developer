package com.hiwitech.android.main.di

import com.hiwitech.android.main.ActivityMain
import com.hiwitech.android.main.module.ModuleMain
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
abstract class MainBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            // fragments
            ModuleMain::class
        ]
    )
    internal abstract fun mainActivity(): ActivityMain
}