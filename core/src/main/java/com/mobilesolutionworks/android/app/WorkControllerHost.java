package com.mobilesolutionworks.android.app;

import com.mobilesolutionworks.android.app.controller.WorksControllerManager;

/**
 * Created by yunarta on 15/3/17.
 */
@FunctionalInterface
public interface WorkControllerHost {

    WorksControllerManager getControllerManager();
}
