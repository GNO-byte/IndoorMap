package com.gno.indoormap.custommapview

import android.content.Context
import android.util.AttributeSet
import com.onlylemi.mapview.library.MapView

class MapPicture : MapView {
    lateinit var activeRoomLayer: RoomLayer

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun activeRoomLayerSsInitialized(): Boolean {
        return ::activeRoomLayer.isInitialized

    }
}