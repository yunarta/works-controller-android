package com.mobilesolutionworks.android.app.test;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.test.espresso.UiController;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.view.View;

import com.mobilesolutionworks.android.app.controller.WorksController;
import com.mobilesolutionworks.android.app.test.util.PerformRootAction;
import com.mobilesolutionworks.android.app.test.util.WaitForIdle;
import com.mobilesolutionworks.android.app.test.works.SchedulerTestActivity;
import com.mobilesolutionworks.android.app.test.works.SchedulerTestFragment;
import com.mobilesolutionworks.android.app.test.works.StateWorksFragment;

import org.junit.Rule;
import org.junit.Test;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;

/**
 * Created by yunarta on 12/3/17.
 */

public class WorksMainSchedulerTest {

    @Rule
    public ActivityTestRule<SchedulerTestActivity> mActivityTestRule = new ActivityTestRule<>(SchedulerTestActivity.class);

    @Test
    public void testRunOnUIWhenIsReady() throws Exception {
        // Context of the app under test.
        final AtomicReference<StateWorksFragment.State> runningState1 = new AtomicReference<>();
        final AtomicReference<StateWorksFragment.State> runningState2 = new AtomicReference<>();
        final AtomicReference<StateWorksFragment.State> runningState3 = new AtomicReference<>();

        final AtomicReference<WeakReference<SchedulerTestFragment>> fragment = new AtomicReference<>();
        final AtomicReference<WeakReference<WorksController>> controller = new AtomicReference<>();

        onView(isRoot()).perform(new PerformRootAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));

                SchedulerTestActivity activity = (SchedulerTestActivity) resumedActivities.get(0);

                SchedulerTestFragment rootFragment = (SchedulerTestFragment) activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                fragment.set(new WeakReference<>(rootFragment));
                controller.set(new WeakReference<>(rootFragment.getController()));
            }
        });

        HandlerThread thread = new HandlerThread("background");
        thread.start();

        Handler handler = new Handler(thread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                controller.get().get().runOnUIWhenIsReady(new Runnable() {
                    @Override
                    public void run() {
                        assertEquals(Thread.currentThread(), Looper.getMainLooper().getThread());
                        runningState1.set(fragment.get().get().getState());
                    }
                });
                controller.get().get().runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        assertEquals(Thread.currentThread(), Looper.getMainLooper().getThread());
                    }
                });
                controller.get().get().runOnMainThreadDelayed(new Runnable() {
                    @Override
                    public void run() {
                        assertEquals(Thread.currentThread(), Looper.getMainLooper().getThread());
                    }
                }, 10);
            }
        });

        onView(isRoot()).perform(new PerformRootAction() {
            @Override
            public void perform(UiController uiController, View view) {
                assertEquals(StateWorksFragment.State.RESUME, fragment.get().get().getState());
                controller.get().get().runOnUIWhenIsReady(new Runnable() {
                    @Override
                    public void run() {
                        assertEquals(Thread.currentThread(), Looper.getMainLooper().getThread());
                        runningState3.set(fragment.get().get().getState());
                    }
                });
                controller.get().get().runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        assertEquals(Thread.currentThread(), Looper.getMainLooper().getThread());
                        assertEquals(StateWorksFragment.State.RESUME, fragment.get().get().getState());
                    }
                });
                controller.get().get().runOnMainThreadDelayed(new Runnable() {
                    @Override
                    public void run() {
                        assertEquals(Thread.currentThread(), Looper.getMainLooper().getThread());
                        assertEquals(StateWorksFragment.State.RESUME, fragment.get().get().getState());
                    }
                }, 10);
            }
        });

        onView(withId(R.id.button)).perform(ViewActions.click());

        onView(isRoot()).perform(new PerformRootAction() {
            @Override
            public void perform(UiController uiController, View view) {
                assertEquals(StateWorksFragment.State.PAUSE, fragment.get().get().getState());
                controller.get().get().runOnUIWhenIsReady(new Runnable() {
                    @Override
                    public void run() {
                        assertEquals(Thread.currentThread(), Looper.getMainLooper().getThread());
                        runningState2.set(fragment.get().get().getState());
                    }
                });
            }
        });

        pressBack();
        onView(isRoot()).perform(new PerformRootAction() {
            @Override
            public void perform(UiController uiController, View view) {
                assertEquals(StateWorksFragment.State.RESUME, runningState1.get());
                assertEquals(StateWorksFragment.State.RESUME, runningState2.get());
                assertEquals(StateWorksFragment.State.RESUME, runningState3.get());
            }
        });
    }
}
