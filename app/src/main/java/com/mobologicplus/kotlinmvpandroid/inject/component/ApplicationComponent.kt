package com.mobologicplus.kotlinmvpandroid.inject.component

import com.mobologicplus.kotlinmvpandroid.MainApplication
import com.mobologicplus.kotlinmvpandroid.inject.module.ApplicationModule
import dagger.Component

/**
 * Created by sunil on 5/30/2017.
 */
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(application: MainApplication)
}