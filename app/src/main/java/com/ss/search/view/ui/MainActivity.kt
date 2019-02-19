package com.ss.search.view.ui

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.support.v7.widget.SearchView
import android.transition.Visibility
import android.view.View
import android.widget.Toast
import com.ss.search.R
import com.ss.search.model.News
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SearchItemListFragment.OnListFragmentInteractionListener,
    DetailFragment.OnFragmentInteractionListener {

    private val TAG = "MainActivity"

    override fun onFragmentInteraction(uri: Uri) {
        Toast.makeText(this, getString(R.string.detailed_fragment_interaction), Toast.LENGTH_SHORT)
    }

    override fun onListFragmentInteraction(item: News?) {
        if (item == null) {
            Toast.makeText(this, getString(R.string.list_fragment_interaction), Toast.LENGTH_SHORT)
        } else {
            loadDetailFragment(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setQueryHint(getString(R.string.search_hint))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d(TAG, query)
                defaultText.visibility = View.GONE
                loadSearchListFragment(query);
                return false
            }
        })

        return true
    }

    private fun loadSearchListFragment(query: String) {
        val newFrag = SearchItemListFragment.newInstance(1, query)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_holder,
                newFrag,
                SearchItemListFragment.TAG
            )
            .commitNow()
    }

    private fun loadDetailFragment(news: News) {
        val articleFragment = news.webUrl?.let { DetailFragment.newInstance(news, it) }
        supportFragmentManager
            .beginTransaction()
            .addToBackStack("article")
            .replace(
                R.id.fragment_holder,
                articleFragment, null
            ).commit()
    }
}
