package com.mobilesolutionworks.android.controller;

import android.os.Bundle;

import com.mobilesolutionworks.android.app.WorkControllerHost;
import com.mobilesolutionworks.android.app.controller.WorksControllerManager;

/**
 * Created by yunarta on 15/3/17.
 */

public class HostWorksControllerBuilder {

    public static <C extends HostWorksController<H>, H extends WorkControllerHost> C create(H host, int id, Bundle args, WorksControllerManager.ControllerCallbacks<C> callback) {
        C controller = host.getControllerManager().initController(id, args, callback);
        controller.updateHost(host);
        return controller;
    }
}
