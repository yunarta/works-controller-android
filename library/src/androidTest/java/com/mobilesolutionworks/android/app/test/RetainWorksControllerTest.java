package com.mobilesolutionworks.android.app.test;

import android.app.Activity;
import android.support.test.espresso.UiController;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.support.v4.app.Fragment;
import android.view.Surface;
import android.view.View;

import com.linkedin.android.testbutler.TestButler;
import com.mobilesolutionworks.android.app.controller.WorksController;
import com.mobilesolutionworks.android.app.test.util.PerformRootAction;
import com.mobilesolutionworks.android.app.test.works.EmptyWorksFragment;
import com.mobilesolutionworks.android.app.test.works.RetainWorksControllerActivity;

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
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertNull;

/**
 * Overview
 * <p>
 * As per support lib 25.1.0, {@link Fragment#setRetainInstance(boolean)} is now working on nested
 * fragment, however if the nested fragment is moved to back stack along with the parent after certain
 * configuration changes the nested fragment will be destroyed.
 * <p>
 * <p>
 * <ul>
 * <li>Test whether the behavior as per premise above is correct</li>
 * </ul>
 * Created by yunarta on 9/3/17.
 */
public class RetainWorksControllerTest {

    @Rule
    public ActivityTestRule<RetainWorksControllerActivity> mActivityTestRule = new ActivityTestRule<>(RetainWorksControllerActivity.class);

    @Test
    public void testControllerRetainBehavior() throws Exception {


        // Context of the app under test.
        final AtomicReference<Integer> activityHash = new AtomicReference<>();
        final AtomicReference<Integer> rootFragmentHash = new AtomicReference<>();
        final AtomicReference<Integer> childFragmentHash = new AtomicReference<>();

        final AtomicReference<Integer> rootControllerHash = new AtomicReference<>();
        final AtomicReference<Integer> childControllerHash = new AtomicReference<>();

        onView(isRoot()).perform(new PerformRootAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));

                RetainWorksControllerActivity activity = (RetainWorksControllerActivity) resumedActivities.get(0);
                activityHash.set(System.identityHashCode(activity));

                EmptyWorksFragment rootFragment = (EmptyWorksFragment) activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                assertNotNull(rootFragment);
                rootFragmentHash.set(System.identityHashCode(rootFragment));
                rootControllerHash.set(System.identityHashCode(rootFragment.getController()));

                EmptyWorksFragment child = (EmptyWorksFragment) rootFragment.getChildFragmentManager().findFragmentByTag("child");
                assertNotNull(child);
                childFragmentHash.set(System.identityHashCode(child));
                childControllerHash.set(System.identityHashCode(child.getController()));
            }
        });

        final AtomicReference<WeakReference<WorksController>> addedFragmentWorksController = new AtomicReference<>();

        onView(withId(R.id.button)).perform(ViewActions.click());
        onView(isRoot()).perform(new PerformRootAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));

                RetainWorksControllerActivity activity = (RetainWorksControllerActivity) resumedActivities.get(0);
                assertNotSame("Could not change orientation", activityHash.get(), System.identityHashCode(activity));

                EmptyWorksFragment rootFragment = (EmptyWorksFragment) activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                WorksController controller = rootFragment.getController();

                addedFragmentWorksController.set(new WeakReference<>(controller));
            }
        });

        TestButler.setRotation(Surface.ROTATION_90);
        TestButler.setRotation(Surface.ROTATION_0);

        pressBack();

        TestButler.setRotation(Surface.ROTATION_90);
        TestButler.setRotation(Surface.ROTATION_0);

        Runtime.getRuntime().gc();
        assertNull(addedFragmentWorksController.get().get());

        onView(isRoot()).perform(new PerformRootAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));

                RetainWorksControllerActivity activity = (RetainWorksControllerActivity) resumedActivities.get(0);
                assertNotSame("Could not change orientation", activityHash.get(), System.identityHashCode(activity));

                EmptyWorksFragment rootFragment = (EmptyWorksFragment) activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                assertNotNull(rootFragment);
                assertNotSame("Non retained fragment being maintained over rotation", rootFragmentHash.get(), System.identityHashCode(rootFragment));
                assertEquals("Works controller instance is not maintained in fragment", rootControllerHash.get(), Integer.valueOf(System.identityHashCode(rootFragment.getController())));

                EmptyWorksFragment child = (EmptyWorksFragment) rootFragment.getChildFragmentManager().findFragmentByTag("child");
                assertNotNull(child);
                assertNotSame("Non retained nested fragment being maintained over rotation", childFragmentHash.get(), System.identityHashCode(child));
                assertEquals("Works controller instance is not maintained in nested fragment", childControllerHash.get(), Integer.valueOf(System.identityHashCode(child.getController())));
            }
        });
    }
}
