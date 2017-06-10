//package com.mobilesolutionworks.android.app;
//
//import android.content.res.Configuration;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.DialogFragment;
//
//import com.mobilesolutionworks.android.app.controller.WorksControllerManager;
//
///**
// * DialogFragment host for WorksController.
// * <p>
// * Created by yunarta on 19/11/15.
// */
//public class WorksDialogFragment extends DialogFragment implements WorkControllerHost {
//
//    /**
//     * Controller manager.
//     */
//    private WorksControllerManager mController;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        WorksControllerManager.ControllerManager loader = (WorksControllerManager.ControllerManager) getLoaderManager().initLoader(0, null, new WorksControllerManager.ControllerManagerLoaderCallbacks(getActivity()));
//        mController = loader.getController();
//    }
//
//    /**
//     * Get controller manager to create individual controller.
//     *
//     * @return controller manager.
//     */
//    @Override
//    @NonNull
//    public WorksControllerManager getControllerManager() {
//        return mController;
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mController.getLifecycleHook().onRestoreInstanceState(savedInstanceState);
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        mController.getLifecycleHook().onConfigurationChanged(newConfig);
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        mController.getLifecycleHook().dispatchSaveInstanceState(outState);
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mController.getLifecycleHook().dispatchPause();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mController.getLifecycleHook().dispatchResume();
//    }
//}