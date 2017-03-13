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
import com.mobilesolutionworks.android.app.test.util.ConfigurationChangeLatch;
import com.mobilesolutionworks.android.app.test.util.HostAndController;
import com.mobilesolutionworks.android.app.test.util.PerformRootAction;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static org.junit.Assert.assertEquals;

/**
 * Created by yunarta on 12/3/17.
 */
public class ConfigurationChangesTest extends RotationTest {

    @Rule
    public ActivityTestRule<ConfigurationChangesTestWorksActivity> mActivityTestRule = new ActivityTestRule<>(ConfigurationChangesTestWorksActivity.class);

    @Test
    public void testConfigurationChanges() throws InterruptedException {
        final HostAndController<TestWorksController> activityCheck = new HostAndController<>(false);
        final HostAndController<TestWorksController> fragmentCheck = new HostAndController<>(false);
        final HostAndController<TestWorksController> dialogCheck = new HostAndController<>(false);

        ConfigurationChangeLatch latch = new ConfigurationChangeLatch();
        mActivityTestRule.getActivity().registerComponentCallbacks(latch);
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
        latch.await();

        TestButler.setRotation(Surface.ROTATION_0);
        latch.await();

        onView(isRoot()).perform(new PerformRootAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));

                TestWorksActivity activity = (TestWorksActivity) resumedActivities.get(0);
                activityCheck.validate(activity);
                assertEquals(2, activity.getController().configChangesCount);

                TestWorksFragment fragment = (TestWorksFragment) activity.getSupportFragmentManager().findFragmentById(com.mobilesolutionworks.android.app.test.R.id.fragment_container);
                fragmentCheck.validate(fragment);
                assertEquals(2, fragment.getController().configChangesCount);

                TestWorksDialogFragment dialog = (TestWorksDialogFragment) activity.getSupportFragmentManager().findFragmentByTag("dialog");
                dialogCheck.validate(dialog);
                assertEquals(2, dialog.getController().configChangesCount);
            }
        });
    }
}