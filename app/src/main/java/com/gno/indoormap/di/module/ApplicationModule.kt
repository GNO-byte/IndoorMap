package com.gno.indoormap.di.module

import android.app.Application
import android.content.Context
import com.gno.indoormap.di.ApplicationContext
import com.gno.indoormap.model.Indoor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    fun provideApplication(): Application = application

    @Singleton
    @Provides
    fun indoor(): Indoor {
        return Indoor.initInstance(application.assets)
    }

}