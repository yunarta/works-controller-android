package com.mobilesolutionworks.android.controller.test.basic

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.matcher.ViewMatchers.isRoot
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import android.view.Surface
import android.view.View
import com.linkedin.android.testbutler.TestButler
import com.mobilesolutionworks.android.controller.test.R
import com.mobilesolutionworks.android.controller.test.base.RotationTest
import com.mobilesolutionworks.android.controller.test.util.HostAndController
import com.mobilesolutionworks.android.controller.test.util.PerformRootAction
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by yunarta on 12/3/17.
 */
@RunWith(AndroidJUnit4::class)
class StandardTest : RotationTest() {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(TestWorksActivity::class.java)

    @Test
    @Throws(InterruptedException::class)
    fun testRetainControllerAfterRotation() {
        val activityCheck = HostAndController<TestWorksController>(true)
        val fragmentCheck = HostAndController<TestWorksController>(true)
        val dialogCheck = HostAndController<TestWorksController>(true)

        onView(isRoot()).perform(object : PerformRootAction() {
            override fun perform(uiController: UiController, view: View) {
                val resumedActivities = ArrayList(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED))

                val activity = resumedActivities[0] as TestWorksActivity
                activityCheck.set(activity)

                val manager = activity.supportFragmentManager

                val fragment = manager.findFragmentById(R.id.fragment_container) as TestWorksFragment
                fragmentCheck.set(fragment)

                val dialogFragment = TestWorksDialogFragment()
                dialogFragment.show(manager, "dialog")
                manager.executePendingTransactions()

                dialogCheck.set(dialogFragment)
            }
        })

        TestButler.setRotation(Surface.ROTATION_90)
        mLatch!!.await()

        TestButler.setRotation(Surface.ROTATION_0)
        mLatch!!.await()

        onView(isRoot()).perform(object : PerformRootAction() {
            override fun perform(uiController: UiController, view: View) {
                val resumedActivities = ArrayList(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED))

                val activity = resumedActivities[0] as TestWorksActivity
                activityCheck.validate(activity)

                val fragment = activity.supportFragmentManager.findFragmentById(R.id.fragment_container) as TestWorksFragment
                fragmentCheck.validate(fragment)

                val dialog = activity.supportFragmentManager.findFragmentByTag("dialog") as TestWorksDialogFragment
                dialogCheck.validate(dialog)

                assertEquals(mActivityTestRule.activity.getString(R.string.string_resource), fragment.controller!!.getString(R.string.string_resource))
            }
        })
    }
}