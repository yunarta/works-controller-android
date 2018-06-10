package com.mobilesolutionworks.android.controller.test.complex

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
import com.mobilesolutionworks.android.app.controller.CreateCallback2
import com.mobilesolutionworks.android.app.controller.WorksController
import com.mobilesolutionworks.android.controller.test.base.RotationTest
import com.mobilesolutionworks.android.controller.test.util.PerformRootAction
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by yunarta on 12/3/17.
 */
@RunWith(AndroidJUnit4::class)
class ManualDestroyControllerTest : RotationTest() {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(WorksActivityImpl::class.java)

    @Test
    @Throws(Exception::class)
    fun testManualDestroy() {
        onView(isRoot()).perform(object : PerformRootAction() {
            override fun perform(uiController: UiController, view: View) {
                val resumedActivities = ArrayList(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED))

                val activity = resumedActivities[0] as WorksActivityImpl
                activity.controllerManager.initController(0, null, CreateCallback2 { WorksController(it) })
            }
        })

        TestButler.setRotation(Surface.ROTATION_90)
        mLatch!!.await()

        onView(isRoot()).perform(object : PerformRootAction() {
            override fun perform(uiController: UiController, view: View) {
                val resumedActivities = ArrayList(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED))

                val activity = resumedActivities[0] as WorksActivityImpl
                Assert.assertNotNull(activity.controllerManager.getController(0))
            }
        })

        TestButler.setRotation(Surface.ROTATION_0)
        mLatch!!.await()

        onView(isRoot()).perform(object : PerformRootAction() {
            override fun perform(uiController: UiController, view: View) {
                val resumedActivities = ArrayList(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED))

                val activity = resumedActivities[0] as WorksActivityImpl
                activity.controllerManager.destroyController(0)

                // calling destroy two times should not crash the app
                activity.controllerManager.destroyController(0)
            }
        })

        TestButler.setRotation(Surface.ROTATION_90)
        mLatch!!.await()

        onView(isRoot()).perform(object : PerformRootAction() {
            override fun perform(uiController: UiController, view: View) {
                val resumedActivities = ArrayList(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED))

                val activity = resumedActivities[0] as WorksActivityImpl
                Assert.assertNull(activity.controllerManager.getController(0))
            }
        })
    }
}
