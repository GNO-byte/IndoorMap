package com.gno.indoormap.di.component

import com.gno.indoormap.base.BaseViewModel
import com.gno.indoormap.di.module.ViewModelModule
import com.gno.indoormap.di.scope.FragmentScope
import com.gno.indoormap.map.MapViewModel
import com.gno.indoormap.search.SearchViewModel
import dagger.Component

@FragmentScope
@Component(modules = [ViewModelModule::class])
interface ViewModelComponent {
    fun inject(viewModel: BaseViewModel)
    fun inject(viewModel: MapViewModel)
    fun inject(viewModel: SearchViewModel)
}