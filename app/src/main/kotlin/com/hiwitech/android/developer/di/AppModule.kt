package com.hiwitech.android.developer.di

import android.content.Context
import com.hiwitech.android.developer.ApplicationSimple
import com.hiwitech.android.widget.notify.NotifyManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideContext(application: ApplicationSimple): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providesNotifyManager(context: Context): NotifyManager = NotifyManager(context)
}
