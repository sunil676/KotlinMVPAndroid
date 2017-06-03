package com.mobologicplus.kotlinmvpandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mobologicplus.kotlinmvpandroid.inject.component.DaggerActivityComponent
import com.mobologicplus.kotlinmvpandroid.ui.FriendsListFragment
import com.mobologicplus.kotlinmvpandroid.util.ActivityUtil
import com.mobologicplus.kotlinmvpandroid.inject.module.ActivityModule

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependency()
        ActivityUtil().addFragmentToActivity(getFragmentManager(), FriendsListFragment().newInstance(), R.id.frame, "")
    }

    private fun injectDependency() {
        val applicationComponent = (application as MainApplication).getApplicationComponent()
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()
        activityComponent.inject(this)
    }
}
