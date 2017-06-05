package com.mobologicplus.kotlinmvpandroid.api

import com.mobologicplus.kotlinmvpandroid.model.Friends
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by sunil on 5/30/2017.
 */
interface ApiServiceInterface {

    @GET("getmyfriends")
    fun getMyFriends(): Observable<Friends>

}