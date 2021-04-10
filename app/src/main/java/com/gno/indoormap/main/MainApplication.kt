package com.gno.indoormap.main

import android.app.Application
import com.gno.indoormap.di.component.ApplicationComponent
import com.gno.indoormap.di.component.DaggerApplicationComponent
import com.gno.indoormap.di.module.ApplicationModule
import com.gno.indoormap.model.Indoor
import javax.inject.Inject

class MainApplication : Application() {

    val applicationComponent: ApplicationComponent =
        DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()

    @Inject
    lateinit var indoor: Indoor

    override fun onCreate() {
        super.onCreate()
        applicationComponent.inject(this)
    }


}