package com.mobologicplus.kotlinmvpandroid.base

/**
 * Created by sunil on 5/30/2017.
 */
class BaseContract {

    interface Presenter<in T>{
        fun subscribe()
        fun unSubscribe()
        fun attachView(view : T)
    }

    interface View {

    }
}