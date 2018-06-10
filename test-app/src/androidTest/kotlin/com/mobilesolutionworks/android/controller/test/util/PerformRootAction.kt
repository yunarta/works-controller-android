package com.mobilesolutionworks.android.controller.test.util

import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.isRoot
import android.view.View
import org.hamcrest.Matcher

/**
 * Created by yunarta on 12/3/17.
 */

abstract class PerformRootAction : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return isRoot()
    }

    override fun getDescription(): String? {
        return null
    }
}
