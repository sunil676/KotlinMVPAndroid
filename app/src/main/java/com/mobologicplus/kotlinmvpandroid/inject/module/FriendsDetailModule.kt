package com.mobologicplus.kotlinmvpandroid.inject.module

import com.mobologicplus.kotlinmvpandroid.ui.FriendsDetailContract
import com.mobologicplus.kotlinmvpandroid.ui.FriendsDetailPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by sunil on 6/5/2017.
 */
@Module
class FriendsDetailModule {

    @Provides
    fun getFriendsDetailPresenter() : FriendsDetailContract.Presenter{
        return FriendsDetailPresenter()
    }
}