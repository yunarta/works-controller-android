package com.mobilesolutionworks.android.controller.test.host

import android.app.Application
import android.support.test.InstrumentationRegistry
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
import com.mobilesolutionworks.android.controller.test.util.HostAndHostController
import com.mobilesolutionworks.android.controller.test.util.PerformRootAction
import com.mobilesolutionworks.android.controller.test.util.ResumeLatch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by yunarta on 12/3/17.
 */
@RunWith(AndroidJUnit4::class)
class HostStandardTest {

    private var mLatch: ResumeLatch? = null

    @Before
    fun setupLatch() {
        mLatch = ResumeLatch()

        val application = InstrumentationRegistry.getTargetContext().applicationContext as Application
        application.registerActivityLifecycleCallbacks(mLatch)
    }

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

                val manager = activity.supportFragmentManager

                val fragment = manager.findFragmentById(R.id.fragment_container) as HostWorksControllerFragment
                fragmentCheck.set(fragment)

                val dialogFragment = HostWorksControllerDialogFragment()
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

                val activity = resumedActivities[0] as HostWorksControllerActivity
                activityCheck.validate(activity)

                val fragment = activity.supportFragmentManager.findFragmentById(R.id.fragment_container) as HostWorksControllerFragment
                fragmentCheck.validate(fragment)

                val dialog = activity.supportFragmentManager.findFragmentByTag("dialog") as HostWorksControllerDialogFragment
                dialogCheck.validate(dialog)
            }
        })
    }

    @After
    fun releaseLatch() {
        val application = InstrumentationRegistry.getTargetContext().applicationContext as Application
        application.unregisterActivityLifecycleCallbacks(mLatch)
    }
}