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
import com.mobilesolutionworks.android.app.test.sdk.RetainChildFragmentActivity;
import com.mobilesolutionworks.android.app.test.works.EmptyWorksFragment;
import com.mobilesolutionworks.android.app.test.works.RetainWorksControllerActivity;
import com.mobilesolutionworks.android.app.test.works.RootWorksFragment;

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
 * Overview
 * <p>
 * As per support lib 25.1.0, {@link Fragment#setRetainInstance(boolean)} is now working on nested
 * fragment, however if the nested fragment is moved to back stack along with the parent after certain
 * configuration changes the nested fragment will be destroyed.
 *
 *
 * <ul>
 * <li>Test whether the behavior as per premise above is correct</li>
 * </ul>
 * Created by yunarta on 9/3/17.
 */
public class RetainWorksControllerTest {

    @Rule
    public ActivityTestRule<RetainWorksControllerActivity> mActivityTestRule = new ActivityTestRule<>(RetainWorksControllerActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        final AtomicReference<Integer> activityHash = new AtomicReference<>();
        final AtomicReference<Integer> rootFragmentHash = new AtomicReference<>();
        final AtomicReference<Integer> childFragmentHash = new AtomicReference<>();

        final AtomicReference<Integer> rootControllerHash = new AtomicReference<>();
        final AtomicReference<Integer> childControllerHash = new AtomicReference<>();

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

                RetainWorksControllerActivity activity = (RetainWorksControllerActivity) resumedActivities.get(0);
                activityHash.set(System.identityHashCode(activity));

                EmptyWorksFragment rootFragment = (EmptyWorksFragment) activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                Assert.assertNotNull(rootFragment);
                rootFragmentHash.set(System.identityHashCode(rootFragment));
                rootControllerHash.set(System.identityHashCode(rootFragment.getController()));

                EmptyWorksFragment child = (EmptyWorksFragment) rootFragment.getChildFragmentManager().findFragmentByTag("child");
                Assert.assertNotNull(child);
                childFragmentHash.set(System.identityHashCode(child));
                childControllerHash.set(System.identityHashCode(child.getController()));
            }
        });

        onView(withId(R.id.button)).perform(ViewActions.click());

        TestButler.setRotation(Surface.ROTATION_90);

        TestButler.setRotation(Surface.ROTATION_0);

        pressBack();

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

                RetainWorksControllerActivity activity = (RetainWorksControllerActivity) resumedActivities.get(0);
                Assert.assertNotSame("Could not change orientation", activityHash.get(), System.identityHashCode(activity));

                EmptyWorksFragment rootFragment = (EmptyWorksFragment) activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                Assert.assertNotNull(rootFragment);
                Assert.assertNotSame("Non retained fragment being maintained over rotation", rootFragmentHash.get(), Integer.valueOf(System.identityHashCode(rootFragment)));
                Assert.assertEquals("Works controller instance is not maintained in fragment", rootControllerHash.get(), Integer.valueOf(System.identityHashCode(rootFragment.getController())));

                EmptyWorksFragment child = (EmptyWorksFragment) rootFragment.getChildFragmentManager().findFragmentByTag("child");
                Assert.assertNotNull(child);
                Assert.assertNotSame("Non retained nested fragment being maintained over rotation", childFragmentHash.get(), System.identityHashCode(child));
                Assert.assertEquals("Works controller instance is not maintained in nested fragment", childControllerHash.get(), Integer.valueOf(System.identityHashCode(child.getController())));
            }
        });
    }

}
