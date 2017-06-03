package com.mobologicplus.kotlinmvpandroid.api

import com.google.gson.GsonBuilder
import com.mobologicplus.kotlinmvpandroid.model.Friends
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by sunil on 5/30/2017.
 */
class ApiService {

    val service : ApiServiceInterface
    var baseUrl = "http://demo2974937.mockable.io/"

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()

        service = retrofit.create<ApiServiceInterface>(ApiServiceInterface::class.java)
    }

    fun loadFriends(): Observable<Friends> {
        return service.getMyFriends()
    }
}

