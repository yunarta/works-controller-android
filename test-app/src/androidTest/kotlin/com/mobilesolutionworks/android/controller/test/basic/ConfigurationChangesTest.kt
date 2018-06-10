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
import com.mobilesolutionworks.android.controller.test.util.ConfigurationChangeLatch
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
class ConfigurationChangesTest : RotationTest() {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(ConfigurationChangesTestWorksActivity::class.java)

    @Test
    public fun testConfigurationChanges() {
        val activityCheck = HostAndController<TestWorksController>(false)
        val fragmentCheck = HostAndController<TestWorksController>(false)
        val dialogCheck = HostAndController<TestWorksController>(false)

        val latch = ConfigurationChangeLatch()
        mActivityTestRule.activity.registerComponentCallbacks(latch)
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
        latch.await()

        TestButler.setRotation(Surface.ROTATION_0)
        latch.await()

        onView(isRoot()).perform(object : PerformRootAction() {
            override fun perform(uiController: UiController, view: View) {
                val resumedActivities = ArrayList(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED))

                val activity = resumedActivities[0] as TestWorksActivity
                activityCheck.validate(activity)
                assertEquals(2, activity.controller!!.configChangesCount.toLong())

                val fragment = activity.supportFragmentManager.findFragmentById(R.id.fragment_container) as TestWorksFragment
                fragmentCheck.validate(fragment)
                assertEquals(2, fragment.controller!!.configChangesCount.toLong())

                val dialog = activity.supportFragmentManager.findFragmentByTag("dialog") as TestWorksDialogFragment
                dialogCheck.validate(dialog)
                assertEquals(2, dialog.controller!!.configChangesCount.toLong())
            }
        })
    }
}