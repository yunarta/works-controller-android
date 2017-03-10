package com.mobilesolutionworks.android.app.test;

import android.app.Activity;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.view.Surface;
import android.view.View;

import com.linkedin.android.testbutler.TestButler;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collection;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

public class RotateActivityTest {

    @Rule
    public ActivityTestRule<RotateActivity> mActivityTestRule = new ActivityTestRule<RotateActivity>(RotateActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
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
                Collection<Activity> resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                if (resumedActivities.isEmpty()) {
                    throw new RuntimeException("Could not change orientation");
                }
            }
        });

        TestButler.setRotation(Surface.ROTATION_90);
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
                Collection<Activity> resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                if (resumedActivities.isEmpty()) {
                    throw new RuntimeException("Could not change orientation");
                }

                for (Activity activity : resumedActivities) {
                    System.out.println("mActivityTestRule.getActivity() = " + activity);
                }
            }
        });
    }
}
