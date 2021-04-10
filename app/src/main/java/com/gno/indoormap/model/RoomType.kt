package com.gno.indoormap.model

import com.gno.indoormap.R

enum class RoomType(val idString: Int) {
    SHOPS(R.string.roomType_shops),
    CAFE(R.string.roomType_cafes),
    OTHER(R.string.roomType_other);

    companion object {
        fun cardIconSelection(type: RoomType): Int {
            return when (type) {
                SHOPS -> R.drawable.ic_shop_black_24dp
                CAFE -> R.drawable.ic_cafe_black_24dp
                else -> R.drawable.ic_other_black_24dp
            }
        }
    }
}