package com.mobologicplus.kotlinmvpandroid.ui

import com.mobologicplus.kotlinmvpandroid.base.BaseContract
import com.mobologicplus.kotlinmvpandroid.model.Friends
import java.io.FileInputStream

/**
 * Created by sunil on 6/5/2017.
 */
class FriendsDetailContract {

    interface Presenter : BaseContract.Presenter<View>{
        fun loadUserDetail(userID : String)
    }

    interface View : BaseContract.View{
        fun onLoadUserDetailOk(friends : Friends.User)
        fun showLoadErrorMessage(error : String)
    }

}