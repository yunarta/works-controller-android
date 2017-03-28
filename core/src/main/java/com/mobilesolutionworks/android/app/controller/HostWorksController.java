package com.mobilesolutionworks.android.app.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mobilesolutionworks.android.app.WorkControllerHost;

import java.lang.ref.WeakReference;

/**
 * Controller with host storing feature.
 * <p>
 * In actual development, developer will most likely use this class rather than WorksController.
 * As the host is updated  after device rotation, the controller can consider that the host is
 * always available when making update in {@link #runWhenUiIsReady(Runnable)} scope.
 * <p>
 * Created by yunarta on 15/3/17.
 */

public abstract class HostWorksController<H extends WorkControllerHost> extends WorksController {

    /**
     * Hold weak reference to host.
     */
    private WeakReference<H> mHost;

    @SuppressWarnings("squid:S1604")
    public static <C extends HostWorksController<H>, H extends WorkControllerHost> C create(@NonNull
                                                                                            final H host, int id, @Nullable Bundle args, @NonNull
                                                                                            final WorksControllerManager.ControllerCallbacks<C> callback) {
        // SuppressWarnings("squid:S1604")
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

    /**
     * Update host, called by controller create callback.
     *
     * @param host host for this controller.
     */
    void updateHost(H host) {
        mHost = new WeakReference<>(host);
    }

    @Nullable
    public H getHost() {
        return mHost.get();
    }

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);

        if (mHost == null) {
            throw new IllegalStateException("HostWorksController can only be created using HostWorksController.create function");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHost = null;
    }
}
