package com.hiwitech.android.application.di

import com.hiwitech.android.application.ActivityMain
import com.hiwitech.android.category.module.ModuleCategory
import com.hiwitech.android.demo.module.ModuleDemo
import com.hiwitech.android.detail.activity.ActivityDetail
import com.hiwitech.android.detail.module.ModuleDetail
import com.hiwitech.android.easyfloat.module.ModuleFloat
import com.hiwitech.android.home.module.ModuleHome
import com.hiwitech.android.main.module.ModuleMain
import com.hiwitech.android.me.module.ModuleMe
import com.hiwitech.android.mvvm.di.ActivityScoped
import com.hiwitech.android.navigation.module.ModuleNavigation
import com.hiwitech.android.notify.module.ModuleNotify
import com.hiwitech.android.route.ActivityAroute
import com.hiwitech.android.route.ModuleRoute
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            // fragments
            ModuleHome::class,
            ModuleMain::class,
            ModuleCategory::class,
            ModuleMe::class,
            ModuleDemo::class,
            ModuleDetail::class,
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
