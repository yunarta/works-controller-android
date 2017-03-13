package com.mobilesolutionworks.android.app.test.base;

import android.app.Application;
import android.support.test.InstrumentationRegistry;

import com.mobilesolutionworks.android.app.test.util.ResumeLatch;

import org.junit.After;
import org.junit.Before;

/**
 * Created by yunarta on 12/3/17.
 */

public class RotationTest {

    protected ResumeLatch mLatch;

    @Before
    public void setupLatch() {
        mLatch = new ResumeLatch();

        Application application = (Application) InstrumentationRegistry.getTargetContext().getApplicationContext();
        application.registerActivityLifecycleCallbacks(mLatch);
    }

    @After
    public void releaseLatch() {
        Application application = (Application) InstrumentationRegistry.getTargetContext().getApplicationContext();
        application.unregisterActivityLifecycleCallbacks(mLatch);
    }
}
