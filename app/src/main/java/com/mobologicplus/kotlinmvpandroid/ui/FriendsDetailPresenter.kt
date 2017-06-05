package com.mobologicplus.kotlinmvpandroid.ui

import com.mobologicplus.kotlinmvpandroid.db.DbHelper
import com.mobologicplus.kotlinmvpandroid.db.DbManager
import com.mobologicplus.kotlinmvpandroid.model.Friends
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by 20125908 on 6/5/2017.
 */
class FriendsDetailPresenter : FriendsDetailContract.Presenter{

    private val subscriptions = CompositeDisposable()
    private lateinit var view: FriendsDetailContract.View

    override fun subscribe() {

    }

    override fun unSubscribe() {
        subscriptions.clear()
    }

    override fun attachView(view: FriendsDetailContract.View) {
        this.view = view
    }

    override fun loadUserDetail(userId: String) {
        var observableUser = DbManager().detailFriend(userId)
        var subscribe =  observableUser.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({user: Friends.User? ->
                    view.onLoadUserDetailOk(user!!)
                }, { t: Throwable? -> view.showLoadErrorMessage(t!!.message.toString())})
        subscriptions.add(subscribe)
    }
}