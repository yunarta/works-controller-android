package com.mobilesolutionworks.android.app.test.util;

import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by yunarta on 12/3/17.
 */
public class ConfigurationChangeLatch implements ComponentCallbacks {

    private CountDownLatch mLatch;

    public ConfigurationChangeLatch() {
        mLatch = new CountDownLatch(1);
    }

    public void await() throws InterruptedException {
        mLatch.await(5, TimeUnit.SECONDS);
        reset();
    }

    public void reset() {
        mLatch = new CountDownLatch(1);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        mLatch.countDown();
    }

    @Override
    public void onLowMemory() {

    }
}
