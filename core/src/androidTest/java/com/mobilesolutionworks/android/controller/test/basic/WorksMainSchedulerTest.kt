package com.mobilesolutionworks.android.controller.test.basic

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.UiController
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isRoot
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import android.view.View
import com.mobilesolutionworks.android.app.controller.WorksController
import com.mobilesolutionworks.android.controller.test.R
import com.mobilesolutionworks.android.controller.test.util.PerformRootAction
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.atomic.AtomicReference

/**
 * Created by yunarta on 12/3/17.
 */

public class WorksMainSchedulerTest {

    @Rule
    @JvmField
    public var mActivityTestRule = ActivityTestRule(SchedulerTestActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun testRunOnUIWhenIsReady() {
        // Context of the app under test.
        val runningState1 = AtomicReference<SchedulerWorksFragment.State>()
        val runningState2 = AtomicReference<SchedulerWorksFragment.State>()
        val runningState3 = AtomicReference<SchedulerWorksFragment.State>()

        val fragment = AtomicReference<WeakReference<SchedulerTestFragment>>()
        val controller = AtomicReference<WeakReference<WorksController>>()

        onView(isRoot()).perform(object : PerformRootAction() {
            override fun perform(uiController: UiController, view: View) {
                val resumedActivities = ArrayList(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED))

                val activity = resumedActivities[0] as SchedulerTestActivity

                val rootFragment = activity.supportFragmentManager.findFragmentById(R.id.fragment_container) as SchedulerTestFragment
                val c = rootFragment.controller
                assertEquals(activity.applicationContext, c!!.context)

                fragment.set(WeakReference(rootFragment))
                controller.set(WeakReference(c))
            }
        })

        val thread = HandlerThread("background")
        thread.start()

        val handler = Handler(thread.looper)
        handler.post {
            controller.get().get()?.runWhenUiIsReady(Runnable {
                assertEquals(Thread.currentThread(), Looper.getMainLooper().thread)
                runningState1.set(fragment.get().get()?.state)
            })
            controller.get().get()?.runOnMainThread(Runnable { assertEquals(Thread.currentThread(), Looper.getMainLooper().thread) })
            controller.get().get()?.runOnMainThreadDelayed(Runnable { assertEquals(Thread.currentThread(), Looper.getMainLooper().thread) }, 10)
        }

        onView(isRoot()).perform(object : PerformRootAction() {
            override fun perform(uiController: UiController, view: View) {
                assertEquals(SchedulerWorksFragment.State.RESUME, fragment.get().get()?.state)
                controller.get().get()?.runWhenUiIsReady(Runnable {
                    assertEquals(Thread.currentThread(), Looper.getMainLooper().thread)
                    runningState3.set(fragment.get().get()?.state)
                })
                controller.get().get()?.runOnMainThread(Runnable {
                    assertEquals(Thread.currentThread(), Looper.getMainLooper().thread)
                    assertEquals(SchedulerWorksFragment.State.RESUME, fragment.get().get()?.state)
                })
                controller.get().get()?.runOnMainThreadDelayed(Runnable {
                    assertEquals(Thread.currentThread(), Looper.getMainLooper().thread)
                    assertEquals(SchedulerWorksFragment.State.RESUME, fragment.get().get()?.state)
                }, 10)
            }
        })

        onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click())

        onView(isRoot()).perform(object : PerformRootAction() {
            override fun perform(uiController: UiController, view: View) {
                assertEquals(SchedulerWorksFragment.State.PAUSE, fragment.get().get()?.state)
                controller.get().get()?.runWhenUiIsReady(Runnable {
                    assertEquals(Thread.currentThread(), Looper.getMainLooper().thread)
                    runningState2.set(fragment.get().get()?.state)
                })
            }
        })

        pressBack()
        onView(isRoot()).perform(object : PerformRootAction() {
            override fun perform(uiController: UiController, view: View) {
                assertEquals(SchedulerWorksFragment.State.RESUME, runningState1.get())
                assertEquals(SchedulerWorksFragment.State.RESUME, runningState2.get())
                assertEquals(SchedulerWorksFragment.State.RESUME, runningState3.get())
            }
        })
    }
}
