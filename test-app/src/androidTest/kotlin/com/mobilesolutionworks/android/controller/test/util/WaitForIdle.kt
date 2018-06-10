package com.mobilesolutionworks.android.controller.test.util

import android.support.test.espresso.UiController
import android.view.View

/**
 * Created by yunarta on 12/3/17.
 */

class WaitForIdle @JvmOverloads constructor(private val mMillisDelay: Int = 100) : PerformRootAction() {

    override fun perform(uiController: UiController, view: View) {
        uiController.loopMainThreadForAtLeast(mMillisDelay.toLong())
        uiController.loopMainThreadUntilIdle()
    }
}
