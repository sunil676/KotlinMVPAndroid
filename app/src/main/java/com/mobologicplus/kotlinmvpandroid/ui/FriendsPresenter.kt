package com.mobologicplus.kotlinmvpandroid.ui

import com.mobologicplus.kotlinmvpandroid.api.ApiService
import com.mobologicplus.kotlinmvpandroid.db.DbManager
import com.mobologicplus.kotlinmvpandroid.model.Friends
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by sunil on 5/30/2017.
 */
class FriendsPresenter : FriendsContract.Presenter{

    private val subscriptions = CompositeDisposable()
    private lateinit var view: FriendsContract.View

    override fun loadFriendsDb() {
       // var observableFriends = DbManager().loadFriends();
        //updateView(observableFriends, false)
    }

    override fun loadFriendsAPI() {
        getFriends(true)
    }

    override fun loadDetailFriend(ignoreCache: Boolean) {

    }

    override fun attachView(view: FriendsContract.View) {
        this.view = view
    }

    override fun subscribe() {
       // getFriends(false)
    }

    override fun unSubscribe() {
        subscriptions.clear()
    }

    fun getFriends(isSubcribes : Boolean){
       if (isSubcribes) {
           var observableFriends = ApiService().loadFriends()
           updateView(observableFriends, true)
       }
    }

    fun updateView( observableFriends: Observable<Friends>, isAPI : Boolean){
       var subscribe =  observableFriends.subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
               .subscribe({friends: Friends? ->
                   if (isAPI){
                       // insert into db
                       var listUsers = friends!!.user;
                       listUsers?.let {
                           for (users in it) {
                               DbManager().saveFriendsList(users)
                           }
                       }
                       view.onLoadFriendsOk(listUsers!!)}},
                       { t: Throwable? -> view.showEmptyView(true)})
                       subscriptions.add(subscribe)
        }

}