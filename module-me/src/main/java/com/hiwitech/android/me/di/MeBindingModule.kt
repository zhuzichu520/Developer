package com.hiwitech.android.me.di

import com.hiwitech.android.me.ActivityMain
import com.hiwitech.android.me.module.ModuleMe
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
abstract class MeBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            // fragments
            ModuleMe::class
        ]
    )
    internal abstract fun mainActivity(): ActivityMain
}