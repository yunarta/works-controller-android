package com.mobilesolutionworks.android.controller.test.host;

import android.app.Activity;
import android.app.Application;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.support.v4.app.FragmentManager;
import android.view.Surface;
import android.view.View;

import com.linkedin.android.testbutler.TestButler;
import com.mobilesolutionworks.android.controller.test.R;
import com.mobilesolutionworks.android.controller.test.util.HostAndHostController;
import com.mobilesolutionworks.android.controller.test.util.PerformRootAction;
import com.mobilesolutionworks.android.controller.test.util.ResumeLatch;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

/**
 * Created by yunarta on 12/3/17.
 */
public class HostStandardTest {

    private ResumeLatch mLatch;

    @Before
    public void setupLatch() {
        mLatch = new ResumeLatch();

        Application application = (Application) InstrumentationRegistry.getTargetContext().getApplicationContext();
        application.registerActivityLifecycleCallbacks(mLatch);
    }

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

                FragmentManager manager = activity.getSupportFragmentManager();

                HostWorksControllerFragment fragment = (HostWorksControllerFragment) manager.findFragmentById(R.id.fragment_container);
                fragmentCheck.set(fragment);

                HostWorksControllerDialogFragment dialogFragment = new HostWorksControllerDialogFragment();
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

                HostWorksControllerActivity activity = (HostWorksControllerActivity) resumedActivities.get(0);
                activityCheck.validate(activity);

                HostWorksControllerFragment fragment = (HostWorksControllerFragment) activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                fragmentCheck.validate(fragment);

                HostWorksControllerDialogFragment dialog = (HostWorksControllerDialogFragment) activity.getSupportFragmentManager().findFragmentByTag("dialog");
                dialogCheck.validate(dialog);
            }
        });
    }

    @After
    public void releaseLatch() {
        Application application = (Application) InstrumentationRegistry.getTargetContext().getApplicationContext();
        application.unregisterActivityLifecycleCallbacks(mLatch);
    }
}