package com.mobilesolutionworks.android.controller.samples.ui.activity.demo1;

import android.os.Bundle;
import android.util.Log;

import com.mobilesolutionworks.android.controller.HostWorksController;

/**
 * Created by yunarta on 19/3/17.
 */

public class Demo1ActivityController extends HostWorksController<Demo1Activity> {

    private Thread mThread;

    private boolean mRunning;

    private int mCounter;

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);

        mRunning = true;
        mThread = new Thread(() -> {
            while (mRunning && mCounter <= 9999) {
                mCounter++;
                runWhenUiIsReady(() -> {
                    getHost().postNumber(mCounter);
                });

                try {
                    Thread.sleep(66);
                } catch (InterruptedException e) {
                    Log.d("[demo][#1]", "interrupted", e);
                }
            }
        });
        mThread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRunning = false;
        mThread = null;
    }
}
