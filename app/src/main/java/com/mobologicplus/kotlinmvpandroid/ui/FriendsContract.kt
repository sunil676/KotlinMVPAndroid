package com.mobologicplus.kotlinmvpandroid.ui

import com.mobologicplus.kotlinmvpandroid.base.BaseContract
import com.mobologicplus.kotlinmvpandroid.model.Friends

/**
 * Created by sunil on 5/30/2017.
 */
class FriendsContract {

    interface Presenter : BaseContract.Presenter<View>{
        fun loadFriendsAPI()
        fun loadFriendsDb()
        fun loadDetailFriend(ignoreCache : Boolean)
        fun deleteItem(user : Friends.User)
    }

    interface View : BaseContract.View{
        fun onLoadFriendsOk(friends : List<Friends.User>)
        fun showProgress(show : Boolean)
        fun showLoadErrorMessage(error : String)
        fun showEmptyView(visible : Boolean)
        fun deletedItem(isDeleted : Boolean)

    }
}