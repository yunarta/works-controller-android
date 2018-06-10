package com.mobilesolutionworks.android.controller.test.nested

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
import com.mobilesolutionworks.android.app.controller.WorksController
import com.mobilesolutionworks.android.controller.test.R
import com.mobilesolutionworks.android.controller.test.base.RotationTest
import com.mobilesolutionworks.android.controller.test.util.HostAndController
import com.mobilesolutionworks.android.controller.test.util.PerformRootAction
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.atomic.AtomicReference

/**
 * Overview
 * <p>
 * As per support lib 25.1.0, {@link Fragment#setRetainInstance(boolean)} is now working on nested
 * fragment, however if the nested fragment is moved to back stack along with the parent after certain
 * configuration changes the nested fragment will be destroyed.
 * <p>
 * <p>
 * <ul>
 * <li>Test whether the behavior as per premise above is correct</li>
 * </ul>
 * Created by yunarta on 9/3/17.
 */
@RunWith(AndroidJUnit4::class)
class NestedWorksFragmentTest : RotationTest() {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(RetainWorksControllerActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun testControllerRetainBehavior() {
        // Context of the app under test.
        val activityHash = AtomicReference<Int>()
        val fragmentCheck = HostAndController<WorksController>()
        val nestedFragmentCheck = HostAndController<WorksController>()

        onView(isRoot()).perform(object : PerformRootAction() {
            override fun perform(uiController: UiController, view: View) {
                val resumedActivities = ArrayList(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED))

                val activity = resumedActivities[0] as RetainWorksControllerActivity
                activityHash.set(System.identityHashCode(activity))

                val root = activity.supportFragmentManager.findFragmentById(R.id.fragment_container) as EmptyWorksFragment
                fragmentCheck.set(root)

                val nested = root.childFragmentManager.findFragmentByTag("child") as EmptyWorksFragment
                nestedFragmentCheck.set(nested)

            }
        })

        val addedFragmentWorksController = AtomicReference<WeakReference<WorksController>>()

        onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click())
        onView(isRoot()).perform(object : PerformRootAction() {
            override fun perform(uiController: UiController, view: View) {
                val resumedActivities = ArrayList(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED))

                val activity = resumedActivities[0] as RetainWorksControllerActivity
                assertNotSame("Could not change orientation", activityHash.get(), System.identityHashCode(activity))

                val rootFragment = activity.supportFragmentManager.findFragmentById(R.id.fragment_container) as EmptyWorksFragment
                val controller = rootFragment.controller

                addedFragmentWorksController.set(WeakReference<WorksController>(controller))
            }
        })

        TestButler.setRotation(Surface.ROTATION_90)
        mLatch!!.await()

        TestButler.setRotation(Surface.ROTATION_0)
        mLatch!!.await()

        pressBack()

        TestButler.setRotation(Surface.ROTATION_90)
        mLatch!!.await()

        TestButler.setRotation(Surface.ROTATION_0)
        mLatch!!.await()

        Runtime.getRuntime().gc()
        assertNull(addedFragmentWorksController.get().get())

        onView(isRoot()).perform(object : PerformRootAction() {
            override fun perform(uiController: UiController, view: View) {
                val resumedActivities = ArrayList(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED))

                val activity = resumedActivities[0] as RetainWorksControllerActivity
                assertNotSame("Could not change orientation", activityHash.get(), System.identityHashCode(activity))

                val root = activity.supportFragmentManager.findFragmentById(R.id.fragment_container) as EmptyWorksFragment
                fragmentCheck.validate(root)

                val nested = root.childFragmentManager.findFragmentByTag("child") as EmptyWorksFragment
                nestedFragmentCheck.validate(nested)
            }
        })
    }
}
