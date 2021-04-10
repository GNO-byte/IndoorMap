package com.gno.indoormap.model

import android.content.res.AssetManager
import com.google.gson.*
import java.lang.reflect.Type

class Indoor private constructor(assetManager: AssetManager) {

    private var floors: List<Floor> = constructorFromJSON(assetManager)

    private fun constructorFromJSON(assetManager: AssetManager): List<Floor> {

        val newFloors = ArrayList<Floor>()
        createFloorNew(
            1,
            assetManager,
            "mapFirstFloorJSON.txt",
            "mapFirstFloorImage.png",
            newFloors
        )
        return newFloors
    }

    private fun createFloorNew(
        numberFloor: Int,
        assetManager: AssetManager,
        nameJsonFile: String,
        nameMapFile: String,
        newFloors: ArrayList<Floor>
    ) {

        val inputStream = assetManager.open(nameJsonFile)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        val textJSON = String(buffer)

        val builder = GsonBuilder()
        builder.registerTypeAdapter(RoomType::class.java, RoomTypeDeserializer())
        val rooms = builder.create().fromJson(textJSON, Rooms::class.java)

        for (room in rooms) {
            room.numberFloor = numberFloor
        }

        val firstFloor = Floor(numberFloor, rooms, nameMapFile)
        newFloors.add(firstFloor)

    }

    internal inner class RoomTypeDeserializer : JsonDeserializer<RoomType> {
        @Throws(JsonParseException::class)
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): RoomType {
            return when (json.asInt) {
                1 -> RoomType.SHOPS
                2 -> RoomType.CAFE
                else -> RoomType.OTHER
            }
        }
    }

    companion object {

        private lateinit var instance: Indoor

        @Synchronized
        fun initInstance(assetManager: AssetManager): Indoor {
            if (::instance.isInitialized) {
                return instance
            }
            instance = Indoor(assetManager)
            return instance
        }
    }

    fun getFloor(number: Int): Floor {
        return if (number <= 0) floors[0] else floors[number - 1]
    }

    fun getAllRooms(): List<Room> {
        val rooms = ArrayList<Room>()
        for (floor in floors) {
            rooms.addAll(floor.rooms)
        }
        return rooms
    }

//    fun getAllSearchRoom(): List<Room>? {
//        val searchRooms: ArrayList<Room> = ArrayList<Room>()
//        for (floor in floors!!) {
//            for (room in floor.rooms) {
//                val searchRoom = SearchRoom(room, floor.number)
//                searchRooms.add(searchRoom)
//            }
//        }
//        return searchRooms
//    }

    fun getRoom(currentRoomName: String, currentFloorNumber: Int): Room? {

        if (currentRoomName == "" || currentFloorNumber == 0) {
            return null
        }

        for (floor in floors) {
            if (floor.number == currentFloorNumber) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    return floor.rooms
                        .stream()
                        .filter { room1 -> room1.name == currentRoomName }
                        .findFirst()
                        .orElse(null)
                } else {
                    for (room in floor.rooms) {
                        if (room.name == currentRoomName) {
                            return room
                        }
                    }
                }
            }
        }

        return null
    }
}