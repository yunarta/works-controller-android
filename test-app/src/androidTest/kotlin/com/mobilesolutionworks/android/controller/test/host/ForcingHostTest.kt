package com.mobilesolutionworks.android.controller.test.host

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.matcher.ViewMatchers.isRoot
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import android.view.View
import com.mobilesolutionworks.android.app.controller.CreateCallback2
import com.mobilesolutionworks.android.controller.test.util.HostAndHostController
import com.mobilesolutionworks.android.controller.test.util.PerformRootAction
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by yunarta on 15/3/17.
 */
@RunWith(AndroidJUnit4::class)
class ForcingHostTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(HostWorksControllerActivity::class.java)

    @Test
    @Throws(InterruptedException::class)
    fun testHostController() {
        val activityCheck = HostAndHostController<HostWorksControllerActivity.ActivityControllerImpl>(true)
        val fragmentCheck = HostAndHostController<HostWorksControllerFragment.FragmentControllerImpl>(true)
        val dialogCheck = HostAndHostController<HostWorksControllerDialogFragment.DialogFragmentControllerImpl>(true)

        onView(isRoot()).perform(object : PerformRootAction() {
            override fun perform(uiController: UiController, view: View) {
                val resumedActivities = ArrayList(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED))

                val activity = resumedActivities[0] as HostWorksControllerActivity
                activityCheck.set(activity)

                try {
                    activity.controllerManager.initController(1, null, CreateCallback2 { HostWorksControllerActivity.ActivityControllerImpl(it) })
                    Assert.fail("HostWorksController should not be created directly")
                } catch (e: Exception) {
                    // no action
                }
            }
        })
    }
}
