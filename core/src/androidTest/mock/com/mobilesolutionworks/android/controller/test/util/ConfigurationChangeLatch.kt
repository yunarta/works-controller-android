package com.mobilesolutionworks.android.controller.test.util

import android.content.ComponentCallbacks
import android.content.res.Configuration

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Created by yunarta on 12/3/17.
 */
class ConfigurationChangeLatch : ComponentCallbacks {

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

    override fun onConfigurationChanged(newConfig: Configuration) {
        mLatch!!.countDown()
    }

    override fun onLowMemory() {

    }
}
