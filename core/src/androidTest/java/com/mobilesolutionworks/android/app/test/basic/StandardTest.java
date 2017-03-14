package com.mobilesolutionworks.android.app.test.basic;

import android.app.Activity;
import android.support.test.espresso.UiController;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.support.v4.app.FragmentManager;
import android.view.Surface;
import android.view.View;

import com.linkedin.android.testbutler.TestButler;
import com.mobilesolutionworks.android.app.test.base.RotationTest;
import com.mobilesolutionworks.android.app.test.util.HostAndController;
import com.mobilesolutionworks.android.app.test.util.PerformRootAction;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

/**
 * Created by yunarta on 12/3/17.
 */
public class StandardTest extends RotationTest {

    @Rule
    public ActivityTestRule<TestWorksActivity> mActivityTestRule = new ActivityTestRule<>(TestWorksActivity.class);

    @Test
    public void testRetainControllerAfterRotation() throws InterruptedException {
        final HostAndController<TestWorksController> activityCheck = new HostAndController<>(true);
        final HostAndController<TestWorksController> fragmentCheck = new HostAndController<>(true);
        final HostAndController<TestWorksController> dialogCheck = new HostAndController<>(true);

        onView(isRoot()).perform(new PerformRootAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));

                TestWorksActivity activity = (TestWorksActivity) resumedActivities.get(0);
                activityCheck.set(activity);

                FragmentManager manager = activity.getSupportFragmentManager();

                TestWorksFragment fragment = (TestWorksFragment) manager.findFragmentById(com.mobilesolutionworks.android.app.test.R.id.fragment_container);
                fragmentCheck.set(fragment);

                TestWorksDialogFragment dialogFragment = new TestWorksDialogFragment();
                dialogFragment.show(manager, "dialog");
                manager.executePendingTransactions();

                dialogCheck.set(dialogFragment);
            }
        });

        TestButler.setRotation(Surface.ROTATION_90);
        mLatch.await();

        TestButler.setRotation(Surface.ROTATION_0);
        mLatch.await();

        onView(isRoot()).perform(new PerformRootAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));

                TestWorksActivity activity = (TestWorksActivity) resumedActivities.get(0);
                activityCheck.validate(activity);

                TestWorksFragment fragment = (TestWorksFragment) activity.getSupportFragmentManager().findFragmentById(com.mobilesolutionworks.android.app.test.R.id.fragment_container);
                fragmentCheck.validate(fragment);

                TestWorksDialogFragment dialog = (TestWorksDialogFragment) activity.getSupportFragmentManager().findFragmentByTag("dialog");
                dialogCheck.validate(dialog);
            }
        });
    }
}