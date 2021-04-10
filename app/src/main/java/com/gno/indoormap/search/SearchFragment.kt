package com.gno.indoormap.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.gno.indoormap.databinding.SearchFragmentBinding
import com.gno.indoormap.di.component.DaggerFragmentComponent
import com.gno.indoormap.di.component.FragmentComponent
import com.gno.indoormap.di.module.FragmentModule
import com.gno.indoormap.main.MainActivity
import com.gno.indoormap.main.MainApplication
import com.gno.indoormap.model.Room
import javax.inject.Inject

class SearchFragment : Fragment() {

    private lateinit var fragmentComponent: FragmentComponent
    private lateinit var searchFragmentBinding: SearchFragmentBinding

    @Inject
    lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var searchRecyclerAdapter: SearchRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //dataBinding
        searchFragmentBinding = SearchFragmentBinding.inflate(
            inflater, container, false
        )
        return searchFragmentBinding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule(this))
            .activityComponent((activity as MainActivity).activityComponent)
            .build()
        fragmentComponent.inject(this)

        createList()
    }

    private fun createList() {

        searchFragmentBinding.searchString.isActivated = true
        searchFragmentBinding.searchString.queryHint = "Type your keyword here"
        searchFragmentBinding.searchString.onActionViewExpanded()
        searchFragmentBinding.searchString.isIconified = false
        searchFragmentBinding.searchString.clearFocus()

        searchFragmentBinding.searchString.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchRecyclerAdapter.filter.filter(newText)
                return false
            }
        })

        viewModel.getAllRooms(activity?.applicationContext as MainApplication)
        viewModel.roomsLiveData.observe(viewLifecycleOwner, {
            searchFragmentBinding.storesList.adapter = searchRecyclerAdapter
            searchRecyclerAdapter.submitFullList(it as ArrayList<Room>)
        })

    }

}