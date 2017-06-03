package com.mobologicplus.kotlinmvpandroid.inject.component

import com.mobologicplus.kotlinmvpandroid.MainActivity
import com.mobologicplus.kotlinmvpandroid.inject.module.ActivityModule
import com.mobologicplus.kotlinmvpandroid.inject.module.FriendsModule
import com.mobologicplus.kotlinmvpandroid.inject.scope.PerActivity
import com.mobologicplus.kotlinmvpandroid.ui.FriendsListFragment
import dagger.Component

/**
 * Created by sunil on 5/30/2017.
 */
@PerActivity
@Component( modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)

}