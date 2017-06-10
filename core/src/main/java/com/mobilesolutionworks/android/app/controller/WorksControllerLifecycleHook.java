package com.mobilesolutionworks.android.app.controller;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.SparseArray;

///**
// * Exposed hook for host to pass their lifecycle.
// * <p>
// * Created by yunarta on 8/3/17.
// */
//public class WorksControllerLifecycleHook {
//
//    /**
//     * Controller manager reference.
//     */
//    private final WorksControllerManager mManager;
//
//    /**
//     * Create instance of lifecycle hook.
//     *
//     * @param manager manager instance.
//     */
//    WorksControllerLifecycleHook(WorksControllerManager manager) {
//        mManager = manager;
//    }
//
//    /**
//     * Hook host dispatch pause to controller.
//     * <p>
//     * This method must be called by host.
//     */
//    public void dispatchPause() {
//        mManager.dispatchPause();
//        SparseArray<WorksController> controllers = mManager.getControllers();
//        int size = controllers.size();
//        for (int i = 0; i < size; i++) {
//            controllers.valueAt(i).onPaused();
//        }
//    }
//
//    /**
//     * Hook host dispatch resume to controller.
//     * <p>
//     * This method must be called by host.
//     */
//    public void dispatchResume() {
//        mManager.dispatchResume();
//        SparseArray<WorksController> controllers = mManager.getControllers();
//        int size = controllers.size();
//        for (int i = 0; i < size; i++) {
//            controllers.valueAt(i).onResume();
//        }
//    }
//
//    /**
//     * Hook host dispatch destroy to controller.
//     * <p>
//     * This method must be called by host.
//     */
//    void dispatchDestroy() {
//        SparseArray<WorksController> controllers = mManager.getControllers();
//        int size = controllers.size();
//        for (int i = 0; i < size; i++) {
//            controllers.valueAt(i).onDestroy();
//        }
//        controllers.clear();
//    }
//
//    public void onConfigurationChanged(Configuration config) {
//        SparseArray<WorksController> controllers = mManager.getControllers();
//        int size = controllers.size();
//        for (int i = 0; i < size; i++) {
//            controllers.valueAt(i).onConfigurationChanged(config);
//        }
//    }
//
//    /**
//     * Hook host dispatch restore instance to controller.
//     * <p>
//     * This method must be called by host.
//     */
//    public void onRestoreInstanceState(Bundle state) {
//        if (state != null) {
//            SparseArray<WorksController> controllers = mManager.getControllers();
//            int size = controllers.size();
//            for (int i = 0; i < size; i++) {
//                Bundle bundle = state.getParcelable(":worksController:" + controllers.keyAt(i));
//                if (bundle != null) {
//                    controllers.valueAt(i).onRestoreInstanceState(bundle);
//                }
//            }
//        }
//    }
//
//    /**
//     * Hook host dispatch save instance state instance to controller.
//     * <p>
//     * This method must be called by host.
//     */
//    public void dispatchSaveInstanceState(Bundle state) {
//        SparseArray<WorksController> controllers = mManager.getControllers();
//        int size = controllers.size();
//        for (int i = 0; i < size; i++) {
//            WorksController controller = controllers.valueAt(i);
//
//            Bundle bundle = new Bundle();
//            controller.onSaveInstanceState(bundle);
//
//            state.putParcelable(":worksController:" + controllers.keyAt(i), bundle);
//        }
//    }
//}
