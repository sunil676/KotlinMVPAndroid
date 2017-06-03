package com.mobologicplus.kotlinmvpandroid.inject.module

import android.app.Activity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by sunil on 5/30/2017.
 */
@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    fun provideActivityContext() : Activity {
        return activity
    }
}