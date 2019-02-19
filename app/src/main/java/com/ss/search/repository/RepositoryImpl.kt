package com.ss.search.repository

import android.arch.lifecycle.MutableLiveData
import com.ss.search.model.News
import retrofit2.Call
import retrofit2.Response
import android.arch.lifecycle.LiveData
import com.ss.search.model.ListingResponse
import com.ss.search.model.NewsResponse
import retrofit2.Callback


class RepositoryImpl(
    private val apiService: ApiService
) {
    val networkState = MutableLiveData<NetworkState>()

    fun getNewsCall(searchKeyword: String): LiveData<List<News>> {
        val data = MutableLiveData<List<News>>()
        networkState.postValue(NetworkState.LOADING)
        apiService.getNews(
            election = searchKeyword,
            apiKey = "d31fe793adf546658bd67e2b6a7fd11a"
        ).enqueue(object : Callback<ListingResponse> {
            override fun onResponse(call: Call<ListingResponse>, response: Response<ListingResponse>) {
                if (response.isSuccessful) {
                    var listResponse: ListingResponse? = response.body()
                    var newsResp: NewsResponse? = listResponse?.newsResponse
                    var news: List<News>? = newsResp?.news
                    val items = news ?: emptyList()
                    data.value = items
                    networkState.postValue(NetworkState.LOADED)
                } else {
                    //TODO: Retry Logic implementation
                    networkState.postValue(
                        NetworkState.error("error code: ${response.code()}")
                    )
                }
            }

            override fun onFailure(call: Call<ListingResponse>, t: Throwable) {
                data.value = null
                networkState.postValue(NetworkState.error(t.message ?: "unknown err"))

            }
        })

        return data
    }
}
