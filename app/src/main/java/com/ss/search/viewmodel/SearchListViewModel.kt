package com.ss.search.viewmodel

import android.arch.lifecycle.LiveData
import android.app.Application
import android.arch.lifecycle.AndroidViewModel

import com.ss.search.model.News
import com.ss.search.repository.ApiService
import com.ss.search.repository.RepositoryImpl

class SearchListViewModel(application: Application , searchQuery: String) : AndroidViewModel(application) {
    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    private val searchListObservable: LiveData<List<News>>

    private val apiService by lazy {
        ApiService.create()
    }

    init {
        searchListObservable = RepositoryImpl(apiService).getNewsCall(searchQuery)
    }

    fun getSearchListObservable(): LiveData<List<News>> {
        return searchListObservable
    }
}