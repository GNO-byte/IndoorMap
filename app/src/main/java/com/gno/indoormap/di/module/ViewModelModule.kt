package com.gno.indoormap.di.module

import androidx.lifecycle.MutableLiveData
import com.gno.indoormap.map.TransferData
import com.gno.indoormap.model.Room
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@Module
class ViewModelModule {

    @Provides
    fun scope(): CoroutineScope = CoroutineScope(Job() + Dispatchers.Default)

    @Provides
    fun transferLiveDate(): MutableLiveData<TransferData> = MutableLiveData<TransferData>()

    @Provides
    fun roomLiveDate(): MutableLiveData<Room> = MutableLiveData<Room>()

    @Provides
    fun roomsLiveDate(): MutableLiveData<List<Room>> = MutableLiveData<List<Room>>()

}