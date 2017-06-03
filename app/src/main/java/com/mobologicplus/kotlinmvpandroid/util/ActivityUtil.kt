package com.mobologicplus.kotlinmvpandroid.util

import android.app.Fragment
import android.app.FragmentManager
/**
 * Created by sunil on 5/30/2017.
 */
class ActivityUtil {

    public fun addFragmentToActivity(fragmentManager: FragmentManager,
                              fragment: Fragment, frameId: Int, fragmentTag: String) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment, fragmentTag)
        transaction.commit()
    }
}