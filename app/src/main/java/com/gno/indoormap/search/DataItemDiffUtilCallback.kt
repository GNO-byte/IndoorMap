package com.gno.indoormap.search

import androidx.recyclerview.widget.DiffUtil
import com.gno.indoormap.model.Room

class DataItemDiffUtilCallback : DiffUtil.ItemCallback<Room>() {

    override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean =
        oldItem.name == newItem.name

}