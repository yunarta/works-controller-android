package com.mobilesolutionworks.android.controller.contrib.test.basic;

import android.app.Activity;
import android.os.Bundle;
import android.support.test.espresso.UiController;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.view.View;

import com.mobilesolutionworks.android.app.controller.WorksControllerManager;
import com.mobilesolutionworks.android.controller.contrib.test.util.HostAndHostController;
import com.mobilesolutionworks.android.controller.test.util.PerformRootAction;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

/**
 * Created by yunarta on 15/3/17.
 */

public class ForcingHostTest {
    @Rule
    public ActivityTestRule<HostWorksControllerActivity> mActivityTestRule = new ActivityTestRule<>(HostWorksControllerActivity.class);

    @Test
    public void testHostController() throws InterruptedException {
        final HostAndHostController<HostWorksControllerActivity.ActivityControllerImpl> activityCheck = new HostAndHostController<>(true);
        final HostAndHostController<HostWorksControllerFragment.FragmentControllerImpl> fragmentCheck = new HostAndHostController<>(true);
        final HostAndHostController<HostWorksControllerDialogFragment.DialogFragmentControllerImpl> dialogCheck = new HostAndHostController<>(true);

        onView(isRoot()).perform(new PerformRootAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));

                HostWorksControllerActivity activity = (HostWorksControllerActivity) resumedActivities.get(0);
                activityCheck.set(activity);

                try {
                    activity.getControllerManager().initController(1, null, new WorksControllerManager.ControllerCallbacks<HostWorksControllerActivity.ActivityControllerImpl>() {
                        @Override
                        public HostWorksControllerActivity.ActivityControllerImpl onCreateController(int id, Bundle args) {
                            return new HostWorksControllerActivity.ActivityControllerImpl();
                        }
                    });

                    Assert.fail("HostWorksController should not be created directly");
                } catch (Exception e) {
                    // no action
                }
            }
        });
    }
}
