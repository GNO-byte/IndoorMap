package com.gno.indoormap.di.module

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gno.indoormap.R
import com.gno.indoormap.map.MapViewModel
import com.gno.indoormap.search.SearchRecyclerAdapter
import com.gno.indoormap.search.SearchViewModel
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: Fragment) {

    @Provides
    fun providesFragment(): Fragment = fragment

    //MapFragment
    @Provides
    fun mapViewModel(): MapViewModel =
        ViewModelProvider(providesFragment()).get(MapViewModel::class.java)

    //SearchFragment
    @Provides
    fun searchViewModel(): SearchViewModel =
        ViewModelProvider(providesFragment()).get(SearchViewModel::class.java)

    @Provides
    fun searchRecyclerAdapter(): SearchRecyclerAdapter =
        SearchRecyclerAdapter { name, numberFloor ->
            onCellClickListener(name, numberFloor)
        }

    private fun onCellClickListener(name: String, numberFloor: Int) {

        fragment.findNavController().navigate(
            R.id.mapFragment, bundleOf(
                "ROOM_NAME" to name,
                "FLOOR_NUMBER" to numberFloor
            )
        )
    }

}