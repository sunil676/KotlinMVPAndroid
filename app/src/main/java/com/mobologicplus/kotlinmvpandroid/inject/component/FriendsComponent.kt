package com.mobologicplus.kotlinmvpandroid.inject.component

import com.mobologicplus.kotlinmvpandroid.inject.module.FriendsModule
import com.mobologicplus.kotlinmvpandroid.inject.scope.PerActivity
import com.mobologicplus.kotlinmvpandroid.ui.FriendsListFragment
import dagger.Component

/**
 * Created by 20125908 on 5/31/2017.
 */
@PerActivity
@Component( modules = arrayOf(FriendsModule::class))
interface FriendsComponent {
    fun inject(friendsListFragment: FriendsListFragment)
}