package com.gno.indoormap.di.component

import android.content.Context
import com.gno.indoormap.di.ActivityContext
import com.gno.indoormap.di.module.ActivityModule
import com.gno.indoormap.di.scope.ActivityScope
import com.gno.indoormap.main.MainActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    @ActivityContext
    fun context(): Context
    fun inject(activity: MainActivity)
}