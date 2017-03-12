package com.mobilesolutionworks.android.app.test.configuration;

import android.content.res.Configuration;

import com.mobilesolutionworks.android.app.controller.WorksController;

/**
 * Created by yunarta on 12/3/17.
 */

public class TestWorksController extends WorksController {

    public int configChangesCount = 0;

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        configChangesCount++;
    }
}
