package com.gno.indoormap.search

import androidx.lifecycle.MutableLiveData
import com.gno.indoormap.base.BaseViewModel
import com.gno.indoormap.main.MainApplication
import com.gno.indoormap.model.Room
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel : BaseViewModel() {
    @Inject
    lateinit var roomsLiveData: MutableLiveData<List<Room>>

    init {
        viewModelComponent.inject(this)
    }

    fun getAllRooms(application: MainApplication) {
        scope.launch {
            roomsLiveData.postValue(application.indoor.getAllRooms())
        }
    }
}