package com.mobilesolutionworks.android.controller.test.base

import android.app.Application
import android.support.test.InstrumentationRegistry

import com.mobilesolutionworks.android.controller.test.util.ResumeLatch

import org.junit.After
import org.junit.Before

/**
 * Created by yunarta on 12/3/17.
 */
abstract class RotationTest {

    var mLatch: ResumeLatch? = null

    @Before
    fun setupLatch() {
        mLatch = ResumeLatch()

        val application = InstrumentationRegistry.getTargetContext().applicationContext as Application
        application.registerActivityLifecycleCallbacks(mLatch)
    }

    @After
    fun releaseLatch() {
        val application = InstrumentationRegistry.getTargetContext().applicationContext as Application
        application.unregisterActivityLifecycleCallbacks(mLatch)
    }
}
