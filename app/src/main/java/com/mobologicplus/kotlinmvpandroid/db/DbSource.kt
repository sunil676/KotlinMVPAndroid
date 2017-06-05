package com.mobologicplus.kotlinmvpandroid.db

import com.mobologicplus.kotlinmvpandroid.model.Friends
import io.reactivex.Observable

/**
 * Created by sunil on 5/30/2017.
 */
interface DbSource {

    fun saveFriendsList(friends: Friends.User)

    fun loadFriends() : Observable<List<Friends.User>>

    fun detailFriend(friendsId : String) : Observable<Friends.User>

    fun deleteFriend(friends: Friends.User) : Observable<Boolean>
}