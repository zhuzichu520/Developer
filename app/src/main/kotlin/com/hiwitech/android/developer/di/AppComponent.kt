package com.hiwitech.android.developer.di

import com.hiwitech.android.mvvm.di.ViewModelModule
import com.hiwitech.android.developer.ApplicationSimple
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        AppModule::class,
        ActivityBindingModule::class
    ]
)

interface AppComponent : AndroidInjector<ApplicationSimple> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: ApplicationSimple): AppComponent
    }
}