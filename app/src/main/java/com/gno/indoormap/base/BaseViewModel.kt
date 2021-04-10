package com.gno.indoormap.base

import androidx.lifecycle.ViewModel
import com.gno.indoormap.di.component.DaggerViewModelComponent
import com.gno.indoormap.di.component.ViewModelComponent
import com.gno.indoormap.di.module.ViewModelModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    val viewModelComponent: ViewModelComponent = DaggerViewModelComponent.builder()
        .viewModelModule(ViewModelModule())
        .build()

    @Inject
    lateinit var scope: CoroutineScope

    init {
        viewModelComponent.inject(this)
    }

    override fun onCleared() {
        scope.cancel()
    }
}