package com.gno.indoormap.map

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gno.indoormap.custommapview.RoomLayer
import com.gno.indoormap.databinding.MapFragmentBinding
import com.gno.indoormap.di.component.DaggerFragmentComponent
import com.gno.indoormap.di.component.FragmentComponent
import com.gno.indoormap.di.module.FragmentModule
import com.gno.indoormap.main.MainActivity
import com.gno.indoormap.main.MainApplication
import com.gno.indoormap.model.Room
import com.gno.indoormap.model.RoomType
import com.onlylemi.mapview.library.MapViewListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapFragment : Fragment() {

    private lateinit var fragmentComponent: FragmentComponent

    @Inject
    lateinit var viewModel: MapViewModel
    lateinit var mapFragmentBinding: MapFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //dataBinding
        mapFragmentBinding = MapFragmentBinding.inflate(
            inflater, container, false
        )

        return mapFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //dagger
        fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule(this))
            .activityComponent((activity as MainActivity).activityComponent)
            .build()
        fragmentComponent.inject(this)

        initMapView()
    }

    private fun initMapView() {

        val currentRoomName = arguments?.getString("ROOM_NAME")
        val currentFloorNumber = arguments?.getInt("FLOOR_NUMBER")

        viewModel.getFloor(
            1,
            activity?.applicationContext as MainApplication,
            currentRoomName ?: "",
            currentFloorNumber ?: 0
        )
        viewModel.transferLiveDate.observe(viewLifecycleOwner, {
            mapFragmentBinding.includeMapPicture.mapPicture.setMapViewListener(object :
                MapViewListener {
                override fun onMapLoadSuccess() {
                    for (room in it.floor.rooms) {
                        lateinit var roomLayer: RoomLayer
                        if (it.currentRoom != null && room == it.currentRoom) {
                            roomLayer = RoomLayer(
                                mapFragmentBinding.includeMapPicture.mapPicture,
                                it.currentRoom.points,
                                true
                            )
                            mapFragmentBinding.includeMapPicture.mapPicture.activeRoomLayer =
                                roomLayer
                            GlobalScope.launch(Dispatchers.Main) {
                                showRoomCard(it.currentRoom)
                            }
                        } else {
                            roomLayer = RoomLayer(
                                mapFragmentBinding.includeMapPicture.mapPicture,
                                room.points,
                                false
                            )
                        }
                        roomLayer.listener = object : RoomLayer.RoomIsClickListener {
                            override fun roomIsClick(visiable: Boolean) {
                                if (visiable) {
                                    showRoomCard(room)
                                } else {
                                    mapFragmentBinding.includeRoomCard.roomCard.visibility =
                                        View.GONE
                                }
                            }
                        }

                        mapFragmentBinding.includeMapPicture.mapPicture.addLayer(roomLayer)
                    }
                    mapFragmentBinding.includeMapPicture.mapPicture.refresh()
                }

                override fun onMapLoadFail() {
                    //Log.i(TAG, "onMapLoadFail");
                }
            })

            mapFragmentBinding.includeMapPicture.mapPicture.loadMap(
                BitmapFactory.decodeStream(
                    activity?.assets?.open(it.floor.mapName)
                )
            )
        })
    }

    fun showRoomCard(room: Room) {
        mapFragmentBinding.includeRoomCard.roomCardName.text =
            room.name
        mapFragmentBinding.includeRoomCard.roomCardGroup.text =
            getString(room.type.idString)
        mapFragmentBinding.includeRoomCard.roomCardPhone.text =
            room.telephone
        mapFragmentBinding.includeRoomCard.roomCard.visibility =
            View.VISIBLE
        mapFragmentBinding.includeRoomCard.roomCardIcon.setImageResource(
            RoomType.cardIconSelection(
                room.type
            )
        )
    }

}