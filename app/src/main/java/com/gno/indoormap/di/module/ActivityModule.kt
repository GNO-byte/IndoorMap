package com.gno.indoormap.di.module

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.gno.indoormap.R
import com.gno.indoormap.di.ActivityContext
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {
    @Provides
    @ActivityContext
    fun provideContext(): Context = activity

    @Provides
    fun navController(): NavController {
        val navHostFragment = (activity as AppCompatActivity).supportFragmentManager
            .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }
}
