package com.hiwitech.android.developer.di

import com.hiwitech.android.developer.ActivityMain
import com.hiwitech.android.developer.ui.aroute.ActivityAroute
import com.hiwitech.android.developer.ui.aroute.ModuleRoute
import com.hiwitech.android.developer.ui.category.module.ModuleCategory
import com.hiwitech.android.developer.ui.demo.main.module.ModuleDemo
import com.hiwitech.android.developer.ui.demo.navigation.module.ModuleNavigation
import com.hiwitech.android.developer.ui.demo.notify.module.ModuleNotify
import com.hiwitech.android.developer.ui.detail.activity.ActivityDetail
import com.hiwitech.android.developer.ui.detail.module.ModuleDetail
import com.hiwitech.android.developer.ui.easyfloat.module.ModuleFloat
import com.hiwitech.android.developer.ui.home.module.ModuleHome
import com.hiwitech.android.developer.ui.main.module.ModuleMain
import com.hiwitech.android.developer.ui.me.module.ModuleMe
import com.hiwitech.android.developer.ui.theme.module.ModuleTheme
import com.hiwitech.android.mvvm.di.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            // fragments
            ModuleMain::class,
            ModuleHome::class,
            ModuleCategory::class,
            ModuleMe::class,
            ModuleDemo::class,
            ModuleDetail::class,
            ModuleTheme::class,
            ModuleNavigation::class,
            ModuleNotify::class,
            ModuleFloat::class
        ]
    )
    internal abstract fun mainActivity(): ActivityMain

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            ModuleDetail::class
        ]
    )
    internal abstract fun detailActivity(): ActivityDetail


    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            ModuleRoute::class
        ]
    )
    internal abstract fun routeActivity(): ActivityAroute
}
