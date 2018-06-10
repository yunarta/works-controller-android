package com.mobilesolutionworks.android.controller.test.sdk


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.UiController
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
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
import com.mobilesolutionworks.android.controller.test.util.PerformRootAction
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import java.util.concurrent.atomic.AtomicReference

/**
 * Overview
 * <p>
 * As per support lib 25.1.0, {@link Fragment#setRetainInstance(boolean)} is now working on nested
 * fragment, however if the nested fragment is moved to back stack along with the parent after certain
 * configuration changes the nested fragment will be destroyed.
 * <ul>
 * <li>Test whether the behavior as per premise above is correct</li>
 * </ul>
 * Created by yunarta on 9/3/17.
 */
@RunWith(AndroidJUnit4::class)
class RetainNestedFragmentTest : RotationTest() {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(RetainChildFragmentActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun testFragmentRetainBehavior() {
        // Context of the app under test.
        val activityHash = AtomicReference<Int>()
        val rootFragmentHash = AtomicReference<Int>()
        val childFragmentHash = AtomicReference<Int>()

        onView(isRoot()).perform(object : PerformRootAction() {
            override fun perform(uiController: UiController, view: View) {
                val resumedActivities = ArrayList(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED))

                val activity = resumedActivities[0] as RetainChildFragmentActivity
                activityHash.set(System.identityHashCode(activity))

                val rootFragment = activity.supportFragmentManager.findFragmentById(R.id.fragment_container)
                Assert.assertNotNull(rootFragment)
                rootFragmentHash.set(System.identityHashCode(rootFragment))

                val child = rootFragment.childFragmentManager.findFragmentByTag("child")
                Assert.assertNotNull(child)
                childFragmentHash.set(System.identityHashCode(child))
            }
        })

        onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click())

        TestButler.setRotation(Surface.ROTATION_90)
        mLatch!!.await()

        TestButler.setRotation(Surface.ROTATION_0)
        mLatch!!.await()

        pressBack()

        onView(isRoot()).perform(object : PerformRootAction() {
            override fun perform(uiController: UiController, view: View) {
                val resumedActivities = ArrayList(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED))

                val activity = resumedActivities[0] as RetainChildFragmentActivity
                Assert.assertNotSame("Could not change orientation", activityHash.get(), System.identityHashCode(activity))

                val rootFragment = activity.supportFragmentManager.findFragmentById(R.id.fragment_container)
                Assert.assertNotNull(rootFragment)
                Assert.assertEquals("Root fragment is different with previous instance", rootFragmentHash.get(), Integer.valueOf(System.identityHashCode(rootFragment)))

                val child = rootFragment.childFragmentManager.findFragmentByTag("child")
                Assert.assertNotNull(child)
                Assert.assertNotSame("If this is failed, it means Android Support retain nested fragment in back stack", childFragmentHash.get(), System.identityHashCode(child))
            }
        })
    }

}
