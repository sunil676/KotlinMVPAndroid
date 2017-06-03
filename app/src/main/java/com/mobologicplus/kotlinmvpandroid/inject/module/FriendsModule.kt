package com.mobologicplus.kotlinmvpandroid.inject.module

import com.mobologicplus.kotlinmvpandroid.ui.FriendsContract
import com.mobologicplus.kotlinmvpandroid.ui.FriendsPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by sunil on 5/30/2017.
 */
@Module
class FriendsModule {

     @Provides
     fun getFriendsPresenter() : FriendsContract.Presenter{
      return FriendsPresenter()
    }
}