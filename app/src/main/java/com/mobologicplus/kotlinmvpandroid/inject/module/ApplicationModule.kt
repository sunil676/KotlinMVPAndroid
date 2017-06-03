package com.mobologicplus.kotlinmvpandroid.inject.module

import android.app.Application
import android.content.Context
import com.mobologicplus.kotlinmvpandroid.MainApplication
import com.mobologicplus.kotlinmvpandroid.inject.scope.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by sunil on 5/30/2017.
 */
@Module
class ApplicationModule(private val mainApplication: MainApplication) {

    @Provides
    @Singleton
    @PerApplication
    fun provideApplicationContext(): Application {
        return mainApplication
    }
}