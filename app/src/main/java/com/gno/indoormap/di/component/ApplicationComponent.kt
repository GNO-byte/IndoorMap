package com.gno.indoormap.di.component

import android.app.Application
import android.content.Context
import com.gno.indoormap.di.ApplicationContext
import com.gno.indoormap.di.module.ApplicationModule
import com.gno.indoormap.main.MainApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    @ApplicationContext
    fun context(): Context
    fun application(): Application
    fun inject(application: MainApplication)
}