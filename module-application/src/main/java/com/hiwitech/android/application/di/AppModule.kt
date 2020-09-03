package com.hiwitech.android.application.di

import android.content.Context
import com.hiwitech.android.application.ApplicationSimple
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: ApplicationSimple): Context {
        return application.applicationContext
    }

}
