package com.gno.indoormap.model

data class Room(
    val name: String,
    val points: List<Point>,
    val telephone: String,
    val type: RoomType,
    var numberFloor: Int
)