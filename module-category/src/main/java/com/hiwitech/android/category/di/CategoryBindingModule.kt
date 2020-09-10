package com.hiwitech.android.category.di

import com.hiwitech.android.category.ActivityMain
import com.hiwitech.android.category.module.ModuleCategory
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
abstract class CategoryBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            // fragments
            ModuleCategory::class
        ]
    )
    internal abstract fun mainActivity(): ActivityMain
}