package com.ss.search.view.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ss.search.R
import com.ss.search.view.adapter.MySearchItemRecyclerViewAdapter

import com.ss.search.model.News

import android.arch.lifecycle.ViewModelProviders
import com.ss.search.viewmodel.SearchListViewModel

import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import com.ss.search.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_searchitem_list.*


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [SearchItemListFragment.OnListFragmentInteractionListener] interface.
 */
class SearchItemListFragment : Fragment() {

    private var columnCount = 1
    private var searchQuery: String? = null
    private var listener: OnListFragmentInteractionListener? = null
    private var searchAdapter: MySearchItemRecyclerViewAdapter? = null

    enum class Status { API_CALL, COMPLETE_EMPTY, COMPLETE_NONEMPTY, ERROR }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
            searchQuery = it.getString(SEARCH_STRING)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_searchitem_list, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        when {
            columnCount <= 1 -> recyclerViewSearch.layoutManager = LinearLayoutManager(context)
            else -> recyclerViewSearch.layoutManager = GridLayoutManager(context, columnCount)
        }
        searchAdapter = MySearchItemRecyclerViewAdapter(listener)
        recyclerViewSearch.adapter = searchAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateView(Status.API_CALL)
        val viewModel = ViewModelProviders.of(
            this, ViewModelFactory(
                activity!!.application,
                this!!.searchQuery!!
            )
        ).get(SearchListViewModel::class.java)

        viewModel.getSearchListObservable().observe(this,
            Observer<List<News>> { news ->
                if (news != null) {
                    updateView(Status.COMPLETE_NONEMPTY)
                    searchAdapter?.setNews(news)
                } else {
                    updateView(Status.COMPLETE_EMPTY)
                }
            })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context

        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun updateView(status: Status) {
        when (status) {
            Status.API_CALL -> {
                recyclerViewSearch.visibility = View.GONE
                textUserMessage.text = getString(R.string.loading)
                layoutInfo.visibility = View.VISIBLE
            }
            Status.COMPLETE_EMPTY -> {
                recyclerViewSearch.visibility = View.GONE
                textUserMessage.text = getString(R.string.empty_result)
                layoutInfo.visibility = View.VISIBLE
            }
            Status.COMPLETE_NONEMPTY -> {
                layoutInfo.visibility = View.GONE
                recyclerViewSearch.visibility = View.VISIBLE
            }
            Status.ERROR -> {
                recyclerViewSearch.visibility = View.GONE
                textUserMessage.text = getString(R.string.error_search)
                layoutInfo.visibility = View.VISIBLE

            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: News?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        const val TAG = "SearchItemListFragment"
        const val SEARCH_STRING = "serach-string"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int, searchString: String) =
            SearchItemListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                    putString(SEARCH_STRING, searchString)
                }
            }
    }
}
