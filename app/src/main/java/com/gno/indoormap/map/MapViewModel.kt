package com.gno.indoormap.map


import androidx.lifecycle.MutableLiveData
import com.gno.indoormap.base.BaseViewModel
import com.gno.indoormap.main.MainApplication
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel : BaseViewModel() {

    @Inject
    lateinit var transferLiveDate: MutableLiveData<TransferData>

    init {
        viewModelComponent.inject(this)
    }

    fun getFloor(
        number: Int,
        application: MainApplication,
        currentRoomName: String,
        currentFloorNumber: Int
    ) {
        scope.launch {
            transferLiveDate.postValue(
                TransferData(
                    application.indoor.getFloor(number),
                    application.indoor.getRoom(currentRoomName, currentFloorNumber)
                )
            )
        }
    }
}