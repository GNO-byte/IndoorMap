package com.gno.indoormap.di.component

import com.gno.indoormap.di.module.FragmentModule
import com.gno.indoormap.di.scope.FragmentScope
import com.gno.indoormap.map.MapFragment
import com.gno.indoormap.search.SearchFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ActivityComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(fragment: SearchFragment)
    fun inject(fragment: MapFragment)
}