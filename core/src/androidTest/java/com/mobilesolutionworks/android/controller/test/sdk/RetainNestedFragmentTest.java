//package com.mobilesolutionworks.android.controller.test.sdk;
//
//import android.app.Activity;
//import android.support.test.espresso.UiController;
//import android.support.test.espresso.action.ViewActions;
//import android.support.test.espresso.matcher.ViewMatchers;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
//import android.support.test.runner.lifecycle.Stage;
//import android.support.v4.app.Fragment;
//import android.view.Surface;
//import android.view.View;
//
//import com.linkedin.android.testbutler.TestButler;
//import com.mobilesolutionworks.android.controller.test.R;
//import com.mobilesolutionworks.android.controller.test.base.RotationTest;
//import com.mobilesolutionworks.android.controller.test.util.PerformRootAction;
//
//import junit.framework.Assert;
//
//import org.junit.Rule;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.concurrent.atomic.AtomicReference;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.Espresso.pressBack;
//import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
//
///**
// * Overview
// * <p>
// * As per support lib 25.1.0, {@link Fragment#setRetainInstance(boolean)} is now working on nested
// * fragment, however if the nested fragment is moved to back stack along with the parent after certain
// * configuration changes the nested fragment will be destroyed.
// * <ul>
// * <li>Test whether the behavior as per premise above is correct</li>
// * </ul>
// * Created by yunarta on 9/3/17.
// */
//public class RetainNestedFragmentTest extends RotationTest {
//
//    @Rule
//    public ActivityTestRule<RetainChildFragmentActivity> mActivityTestRule = new ActivityTestRule<>(RetainChildFragmentActivity.class);
//
//    @Test
//    public void testFragmentRetainBehavior() throws Exception {
//        // Context of the app under test.
//        final AtomicReference<Integer> activityHash = new AtomicReference<>();
//        final AtomicReference<Integer> rootFragmentHash = new AtomicReference<>();
//        final AtomicReference<Integer> childFragmentHash = new AtomicReference<>();
//
//        onView(isRoot()).perform(new PerformRootAction() {
//            @Override
//            public void perform(UiController uiController, View view) {
//                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));
//
//                RetainChildFragmentActivity activity = (RetainChildFragmentActivity) resumedActivities.get(0);
//                activityHash.set(System.identityHashCode(activity));
//
//                Fragment rootFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//                Assert.assertNotNull(rootFragment);
//                rootFragmentHash.set(System.identityHashCode(rootFragment));
//
//                Fragment child = rootFragment.getChildFragmentManager().findFragmentByTag("child");
//                Assert.assertNotNull(child);
//                childFragmentHash.set(System.identityHashCode(child));
//            }
//        });
//
//        onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click());
//
//        TestButler.setRotation(Surface.ROTATION_90);
//        mLatch.await();
//
//        TestButler.setRotation(Surface.ROTATION_0);
//        mLatch.await();
//
//        pressBack();
//
//        onView(isRoot()).perform(new PerformRootAction() {
//            @Override
//            public void perform(UiController uiController, View view) {
//                ArrayList<Activity> resumedActivities = new ArrayList<>(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));
//
//                RetainChildFragmentActivity activity = (RetainChildFragmentActivity) resumedActivities.get(0);
//                Assert.assertNotSame("Could not change orientation", activityHash.get(), System.identityHashCode(activity));
//
//                Fragment rootFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//                Assert.assertNotNull(rootFragment);
//                Assert.assertEquals("Root fragment is different with previous instance", rootFragmentHash.get(), Integer.valueOf(System.identityHashCode(rootFragment)));
//
//                Fragment child = rootFragment.getChildFragmentManager().findFragmentByTag("child");
//                Assert.assertNotNull(child);
//                Assert.assertNotSame("If this is failed, it means Android Support retain nested fragment in back stack", childFragmentHash.get(), System.identityHashCode(child));
//            }
//        });
//    }
//
//}
