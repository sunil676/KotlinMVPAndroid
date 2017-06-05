package com.mobologicplus.kotlinmvpandroid.inject.component

import com.mobologicplus.kotlinmvpandroid.inject.module.FriendsDetailModule
import com.mobologicplus.kotlinmvpandroid.inject.module.FriendsModule
import com.mobologicplus.kotlinmvpandroid.inject.scope.PerActivity
import com.mobologicplus.kotlinmvpandroid.ui.FriendsDetailFragment
import dagger.Component

/**
 * Created by 20125908 on 6/5/2017.
 */
@PerActivity
@Component( modules = arrayOf(FriendsDetailModule::class))
interface FriendsDetailComponent {
    fun inject(friendsDetailFragment: FriendsDetailFragment)
}