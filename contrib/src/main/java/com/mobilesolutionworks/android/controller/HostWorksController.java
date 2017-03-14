package com.mobilesolutionworks.android.controller;

import android.os.Bundle;

import com.mobilesolutionworks.android.app.WorkControllerHost;
import com.mobilesolutionworks.android.app.controller.WorksController;
import com.mobilesolutionworks.android.app.controller.WorksControllerManager;

/**
 * Created by yunarta on 15/3/17.
 */

public abstract class HostWorksController<H extends WorkControllerHost> extends WorksController {

    private H mHost;

    public void updateHost(H host) {
        mHost = host;
    }

    public H getHost() {
        return mHost;
    }

    public static <C extends HostWorksController<H>, H extends WorkControllerHost> C create(H host, int id, Bundle args, WorksControllerManager.ControllerCallbacks<C> callback) {
        C controller = host.getControllerManager().initController(id, args, callback);
        controller.updateHost(host);
        return controller;
    }
}
