package com.mobilesolutionworks.android.app;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.mobilesolutionworks.android.app.controller.WorksControllerManager;

/**
 * DialogFragment host for WorksController.
 * <p>
 * Created by yunarta on 19/11/15.
 */
public class WorksDialogFragment extends DialogFragment {

    private WorksControllerManager mController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WorksControllerManager.InternalLoader loader = (WorksControllerManager.InternalLoader) getLoaderManager().initLoader(0, null, new WorksControllerManager.LoaderCallbacks(getActivity()));
        mController = loader.getController();
    }

    public WorksControllerManager getControllerManager() {
        return mController;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mController.getLifecycleHook().onConfigurationChanged(newConfig);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mController.getLifecycleHook().onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mController.getLifecycleHook().dispatchSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mController.getLifecycleHook().dispatchPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mController.getLifecycleHook().dispatchResume();
    }
}
