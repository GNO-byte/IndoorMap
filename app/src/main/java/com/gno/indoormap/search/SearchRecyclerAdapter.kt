package com.gno.indoormap.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gno.indoormap.R
import com.gno.indoormap.model.Room
import com.gno.indoormap.model.RoomType


class SearchRecyclerAdapter(
    private val cellClickListener: (String, Int) -> Unit
) : ListAdapter<Room, SearchRecyclerAdapter.DataHolder>(DataItemDiffUtilCallback()), Filterable {

    var allSearchRooms = ArrayList<Room>()

    fun submitFullList(list: ArrayList<Room>) {
        allSearchRooms = list
        super.submitList(list)
    }


    override fun getItemCount(): Int {
        return when (val count = super.getItemCount()) {
            0 -> 0
            else -> count
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.stores_list_item, parent, false)
        )
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filteredList: MutableList<Room> = java.util.ArrayList<Room>()
                if (constraint.isEmpty()) {
                    filteredList.addAll(allSearchRooms)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                    for (item in currentList) {
                        if (item.name.toLowerCase().contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                submitList(results.values as List<Room>)
            }
        }
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {

        val item = getItem(position)

        holder.searchCard.setOnClickListener {
            cellClickListener.invoke(item.name, item.numberFloor)
        }


        holder.searchCardName.text = item.name
        holder.searchCardGroup.setText(item.type.idString)

        holder.searchCardIcon.setImageResource(RoomType.cardIconSelection(item.type))

    }

    class DataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var searchCard: CardView = itemView.findViewById(R.id.search_card)
        var searchCardIcon: ImageView = itemView.findViewById(R.id.search_card_icon)
        var searchCardName: TextView = itemView.findViewById(R.id.search_card_name)
        var searchCardGroup: TextView = itemView.findViewById(R.id.search_card_group)

    }


}