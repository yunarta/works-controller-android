package com.mobilesolutionworks.android.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.mobilesolutionworks.android.app.WorkControllerHost;
import com.mobilesolutionworks.android.app.controller.WorksController;
import com.mobilesolutionworks.android.app.controller.WorksControllerManager;

/**
 * Created by yunarta on 15/3/17.
 */

public abstract class HostWorksController<H extends WorkControllerHost> extends WorksController {

    private H mHost;

    void updateHost(H host) {
        mHost = host;
    }

    @NonNull
    public H getHost() {
        return mHost;
    }

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        if (mHost == null) {
            throw new IllegalStateException("HostWorksController can only be created using HostWorksController.create function");
        }
    }

    public static <C extends HostWorksController<H>, H extends WorkControllerHost> C create(final H host, int id, Bundle args, final WorksControllerManager.ControllerCallbacks<C> callback) {
        C controller = host.getControllerManager().initController(id, args, new WorksControllerManager.ControllerCallbacks<C>() {
            @Override
            public C onCreateController(int id, Bundle args) {
                C c = callback.onCreateController(id, args);
                c.updateHost(host);
                return c;
            }
        });

        controller.updateHost(host);
        return controller;
    }
}
