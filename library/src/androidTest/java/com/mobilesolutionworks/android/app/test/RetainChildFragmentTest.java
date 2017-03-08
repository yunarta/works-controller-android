package com.mobilesolutionworks.android.app.test;

import android.app.Activity;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.support.v4.app.Fragment;
import android.view.Surface;
import android.view.View;

import com.linkedin.android.testbutler.TestButler;

import junit.framework.Assert;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by yunarta on 9/3/17.
 */

public class RetainChildFragmentTest {

    @Rule
    public ActivityTestRule<RetainChildFragmentActivity> mActivityTestRule = new ActivityTestRule<>(RetainChildFragmentActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        final AtomicReference<Integer> activityHash = new AtomicReference<>();
        final AtomicReference<Integer> rootFragmentHash = new AtomicReference<>();
        final AtomicReference<Integer> childFragmentHash = new AtomicReference<>();

        onView(isRoot()).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));

                RetainChildFragmentActivity activity = (RetainChildFragmentActivity) resumedActivities.get(0);
                activityHash.set(System.identityHashCode(activity));

                Fragment rootFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                Assert.assertNotNull(rootFragment);
                rootFragmentHash.set(System.identityHashCode(rootFragment));

                Fragment child = rootFragment.getChildFragmentManager().findFragmentByTag("child");
                Assert.assertNotNull(child);
                childFragmentHash.set(System.identityHashCode(child));
            }
        });

        onView(withId(R.id.button)).perform(ViewActions.click());
        Thread.sleep(1);

        TestButler.setRotation(Surface.ROTATION_90);
        Thread.sleep(1);

        TestButler.setRotation(Surface.ROTATION_0);
        Thread.sleep(1);

        pressBack();
        Thread.sleep(1);

        onView(isRoot()).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));

                RetainChildFragmentActivity activity = (RetainChildFragmentActivity) resumedActivities.get(0);
                Assert.assertNotSame("Could not change orientation", activityHash.get(), Integer.valueOf(System.identityHashCode(activity)));

                Fragment rootFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                Assert.assertNotNull(rootFragment);
                Assert.assertEquals(rootFragmentHash.get(), Integer.valueOf(System.identityHashCode(rootFragment)));

                Fragment child = rootFragment.getChildFragmentManager().findFragmentByTag("child");
                Assert.assertNotNull(child);
                Assert.assertNotSame("If this is equals, then Android Support solved this problem", childFragmentHash.get(), Integer.valueOf(System.identityHashCode(child)));
            }
        });
    }

}
