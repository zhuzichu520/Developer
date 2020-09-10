package com.hiwitech.android.demo.di

import com.hiwitech.android.demo.ActivityMain
import com.hiwitech.android.demo.module.ModuleDemo
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
abstract class DemoBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            // fragments
            ModuleDemo::class
        ]
    )
    internal abstract fun mainActivity(): ActivityMain
}