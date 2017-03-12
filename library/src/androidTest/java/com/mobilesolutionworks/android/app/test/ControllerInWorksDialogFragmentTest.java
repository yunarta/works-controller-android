package com.mobilesolutionworks.android.app.test;

import android.app.Activity;
import android.app.Application;
import android.support.test.espresso.UiController;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.view.Surface;
import android.view.View;

import com.linkedin.android.testbutler.TestButler;
import com.mobilesolutionworks.android.app.test.util.PerformRootAction;
import com.mobilesolutionworks.android.app.test.util.ResumeLatch;
import com.mobilesolutionworks.android.app.test.works.WorksActivityImpl;
import com.mobilesolutionworks.android.app.test.works.WorksDialogFragmentImpl;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

/**
 * Created by yunarta on 12/3/17.
 */

public class ControllerInWorksDialogFragmentTest {

    @Rule
    public ActivityTestRule<WorksActivityImpl> mActivityTestRule = new ActivityTestRule<>(WorksActivityImpl.class);

    @Test
    public void testControllerInActivity() throws InterruptedException {
        // Context of the app under test.
        final AtomicReference<Integer> dialogHash = new AtomicReference<>();
        final AtomicReference<Integer> dialogControllerHash = new AtomicReference<>();

        ResumeLatch latch = new ResumeLatch();

        Application application = mActivityTestRule.getActivity().getApplication();
        application.registerActivityLifecycleCallbacks(latch);

        onView(isRoot()).perform(new PerformRootAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));

                WorksActivityImpl activity = (WorksActivityImpl) resumedActivities.get(0);

                WorksDialogFragmentImpl impl = new WorksDialogFragmentImpl();
                impl.show(activity.getSupportFragmentManager(),  "dialog");
            }
        });

        onView(isRoot()).perform(new PerformRootAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));

                WorksActivityImpl activity = (WorksActivityImpl) resumedActivities.get(0);
                WorksDialogFragmentImpl dialog = (WorksDialogFragmentImpl) activity.getSupportFragmentManager().findFragmentByTag("dialog");

                dialogHash.set(System.identityHashCode(dialog));
                dialogControllerHash.set(System.identityHashCode(dialog.getController()));
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

                WorksActivityImpl activity = (WorksActivityImpl) resumedActivities.get(0);
                WorksDialogFragmentImpl dialog = (WorksDialogFragmentImpl) activity.getSupportFragmentManager().findFragmentByTag("dialog");

                assertNotSame("Could not change orientation", dialogHash.get(), System.identityHashCode(dialog));
                assertEquals("Works controller instance is not maintained in fragment", dialogControllerHash.get(), Integer.valueOf(System.identityHashCode(dialog.getController())));
            }
        });
    }

}
