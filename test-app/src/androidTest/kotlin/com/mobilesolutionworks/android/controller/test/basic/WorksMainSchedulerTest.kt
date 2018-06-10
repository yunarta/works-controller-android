package com.mobilesolutionworks.android.controller.test.basic

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by yunarta on 12/3/17.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityConnectedTestCases {

    @JvmField
    @Rule
    var mActivityTestRule = ActivityTestRule(SchedulerTestActivity::class.java)

    @Test
    fun testRunOnUIWhenIsReady() {
//        // Context of the app under test.
//        val runningState1 = AtomicReference<SchedulerWorksFragment.State>()
//        val runningState2 = AtomicReference<SchedulerWorksFragment.State>()
//        val runningState3 = AtomicReference<SchedulerWorksFragment.State>()
//
//        val fragment = AtomicReference<WeakReference<SchedulerTestFragment>>()
//        val controller = AtomicReference<WeakReference<WorksController>>()
//
//        onView(isRoot()).perform(object : PerformRootAction() {
//            override fun perform(uiController: UiController, view: View) {
//                val resumedActivities = ArrayList(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED))
//
//                val activity = resumedActivities[0] as SchedulerTestActivity
//
//                val rootFragment = activity.supportFragmentManager.findFragmentById(R.id.fragment_container) as SchedulerTestFragment
//                val c = rootFragment.controller
//                assertEquals(activity.applicationContext, c!!.context)
//
//                fragment.set(WeakReference(rootFragment))
//                controller.set(WeakReference(c))
//            }
//        })
//
//        val thread = HandlerThread("background")
//        thread.start()
//
//        val handler = Handler(thread.looper)
//        handler.post {
//            controller.get().get()?.runWhenUiIsReady(Runnable {
//                assertEquals(Thread.currentThread(), Looper.getMainLooper().thread)
//                runningState1.set(fragment.get().get()?.state)
//            })
//            controller.get().get()?.runOnMainThread(Runnable { assertEquals(Thread.currentThread(), Looper.getMainLooper().thread) })
//            controller.get().get()?.runOnMainThreadDelayed(Runnable { assertEquals(Thread.currentThread(), Looper.getMainLooper().thread) }, 10)
//        }
//
//        onView(isRoot()).perform(object : PerformRootAction() {
//            override fun perform(uiController: UiController, view: View) {
//                assertEquals(SchedulerWorksFragment.State.RESUME, fragment.get().get()?.state)
//                controller.get().get()?.runWhenUiIsReady(Runnable {
//                    assertEquals(Thread.currentThread(), Looper.getMainLooper().thread)
//                    runningState3.set(fragment.get().get()?.state)
//                })
//                controller.get().get()?.runOnMainThread(Runnable {
//                    assertEquals(Thread.currentThread(), Looper.getMainLooper().thread)
//                    assertEquals(SchedulerWorksFragment.State.RESUME, fragment.get().get()?.state)
//                })
//                controller.get().get()?.runOnMainThreadDelayed(Runnable {
//                    assertEquals(Thread.currentThread(), Looper.getMainLooper().thread)
//                    assertEquals(SchedulerWorksFragment.State.RESUME, fragment.get().get()?.state)
//                }, 10)
//            }
//        })
//
//        onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click())
//
//        onView(isRoot()).perform(object : PerformRootAction() {
//            override fun perform(uiController: UiController, view: View) {
//                assertEquals(SchedulerWorksFragment.State.PAUSE, fragment.get().get()?.state)
//                controller.get().get()?.runWhenUiIsReady(Runnable {
//                    assertEquals(Thread.currentThread(), Looper.getMainLooper().thread)
//                    runningState2.set(fragment.get().get()?.state)
//                })
//            }
//        })
//
//        pressBack()
//        onView(isRoot()).perform(object : PerformRootAction() {
//            override fun perform(uiController: UiController, view: View) {
//                assertEquals(SchedulerWorksFragment.State.RESUME, runningState1.get())
//                assertEquals(SchedulerWorksFragment.State.RESUME, runningState2.get())
//                assertEquals(SchedulerWorksFragment.State.RESUME, runningState3.get())
//            }
//        })
    }
}
