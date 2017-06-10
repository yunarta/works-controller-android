package com.mobilesolutionworks.android.controller.test.util

import android.app.Activity
import android.app.Application
import android.os.Bundle

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Created by yunarta on 12/3/17.
 */
class ResumeLatch : Application.ActivityLifecycleCallbacks {

    private var mLatch: CountDownLatch? = null

    init {
        mLatch = CountDownLatch(1)
    }

    @Throws(InterruptedException::class)
    fun await() {
        mLatch!!.await(5, TimeUnit.SECONDS)
        reset()
    }

    fun reset() {
        mLatch = CountDownLatch(1)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {
        mLatch!!.countDown()
    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}
