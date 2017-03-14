package com.mobilesolutionworks.android.controller.test.complex;

import android.app.Activity;
import android.os.Bundle;
import android.support.test.espresso.UiController;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.view.Surface;
import android.view.View;

import com.linkedin.android.testbutler.TestButler;
import com.mobilesolutionworks.android.app.controller.WorksController;
import com.mobilesolutionworks.android.app.controller.WorksControllerManager;
import com.mobilesolutionworks.android.controller.test.base.RotationTest;
import com.mobilesolutionworks.android.controller.test.util.PerformRootAction;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

/**
 * Created by yunarta on 12/3/17.
 */

public class ManualDestroyControllerTest extends RotationTest {

    @Rule
    public ActivityTestRule<WorksActivityImpl> mActivityTestRule = new ActivityTestRule<>(WorksActivityImpl.class);

    @Test
    public void testManualDestroy() throws Exception {
        onView(isRoot()).perform(new PerformRootAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));

                WorksActivityImpl activity = (WorksActivityImpl) resumedActivities.get(0);
                activity.getControllerManager().initController(0, null, new WorksControllerManager.ControllerCallbacks<WorksController>() {
                    @Override
                    public WorksController onCreateController(int id, Bundle args) {
                        return new WorksController();
                    }
                });
            }
        });

        TestButler.setRotation(Surface.ROTATION_90);
        mLatch.await();

        onView(isRoot()).perform(new PerformRootAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));

                WorksActivityImpl activity = (WorksActivityImpl) resumedActivities.get(0);
                Assert.assertNotNull(activity.getControllerManager().getController(0));
            }
        });

        TestButler.setRotation(Surface.ROTATION_0);
        mLatch.await();

        onView(isRoot()).perform(new PerformRootAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));

                WorksActivityImpl activity = (WorksActivityImpl) resumedActivities.get(0);
                activity.getControllerManager().destroyController(0);

                // calling destroy two times should not crash the app
                activity.getControllerManager().destroyController(0);
            }
        });

        TestButler.setRotation(Surface.ROTATION_90);
        mLatch.await();

        onView(isRoot()).perform(new PerformRootAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));

                WorksActivityImpl activity = (WorksActivityImpl) resumedActivities.get(0);
                Assert.assertNull(activity.getControllerManager().getController(0));
            }
        });
    }
}
