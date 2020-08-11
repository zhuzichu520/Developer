package com.hiwitech.android.developer.di

import android.content.Context
import com.hiwitech.android.developer.ApplicationSimple
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: ApplicationSimple): Context {
        return application.applicationContext
    }

}
