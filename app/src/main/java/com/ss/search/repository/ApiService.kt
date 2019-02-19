package com.ss.search.repository

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.ss.search.model.ListingResponse

interface ApiService {

    @GET("/svc/search/v2/articlesearch.json")
    fun getNews(@Query("q") election: String, @Query("api-key") apiKey: String): Call<ListingResponse>

    companion object {
        private const val BASE_URL = "https://api.nytimes.com/"
        fun create(): ApiService = create(HttpUrl.parse(BASE_URL)!!)
        fun create(httpUrl: HttpUrl): ApiService {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("API", it)
            })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
