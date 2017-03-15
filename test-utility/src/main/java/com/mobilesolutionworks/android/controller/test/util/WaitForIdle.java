package com.mobilesolutionworks.android.controller.test.util;

import android.support.test.espresso.UiController;
import android.view.View;

/**
 * Created by yunarta on 12/3/17.
 */

public class WaitForIdle extends PerformRootAction {

    private int mMillisDelay;

    public WaitForIdle() {
        this(100);
    }

    public WaitForIdle(int millisDelay) {
        mMillisDelay = millisDelay;
    }

    @Override
    public void perform(UiController uiController, View view) {
        uiController.loopMainThreadForAtLeast(mMillisDelay);
        uiController.loopMainThreadUntilIdle();
    }
}
