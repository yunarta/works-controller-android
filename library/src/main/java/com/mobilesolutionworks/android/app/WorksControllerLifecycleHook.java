package com.mobilesolutionworks.android.app;

import android.os.Bundle;
import android.util.SparseArray;

/**
 * Created by yunarta on 8/3/17.
 */

public class WorksControllerLifecycleHook {

    private final WorksControllerManager mManager;

    public WorksControllerLifecycleHook(WorksControllerManager manager) {
        mManager = manager;
    }

    /**
     * Hook host dispatch pause to controller.
     * <p>
     * This method must be called by host.
     */
    public void dispatchPause() {
        SparseArray<WorksControllerManager.ControllerInfo> controllers = mManager.getControllers();
        int size = controllers.size();
        for (int i = 0; i < size; i++) {
            controllers.valueAt(i).controller.onPaused();
        }
    }

    /**
     * Hook host dispatch resume to controller.
     * <p>
     * This method must be called by host.
     */
    public void dispatchResume() {
        SparseArray<WorksControllerManager.ControllerInfo> controllers = mManager.getControllers();
        int size = controllers.size();
        for (int i = 0; i < size; i++) {
            controllers.valueAt(i).controller.onResume();
        }
    }

    /**
     * Hook host dispatch destroy to controller.
     * <p>
     * This method must be called by host.
     */
    void dispatchDestroy() {
        SparseArray<WorksControllerManager.ControllerInfo> controllers = mManager.getControllers();
        int size = controllers.size();
        for (int i = 0; i < size; i++) {
            controllers.valueAt(i).controller.onDestroy();
        }
        controllers.clear();
    }

    /**
     * Hook host dispatch restore instance to controller.
     * <p>
     * This method must be called by host.
     */
    public void onRestoreInstanceState(Bundle state) {
        if (state != null) {
            SparseArray<WorksControllerManager.ControllerInfo> controllers = mManager.getControllers();
            int size = controllers.size();
            for (int i = 0; i < size; i++) {
                Bundle bundle = state.getParcelable(":worksController:" + controllers.keyAt(i));
                controllers.valueAt(i).controller.onViewStateRestored(bundle);
            }
        }
    }

    /**
     * Hook host dispatch save instance state instance to controller.
     * <p>
     * This method must be called by host.
     */
    public void dispatchSaveInstanceState(Bundle state) {
        SparseArray<WorksControllerManager.ControllerInfo> controllers = mManager.getControllers();
        int size = controllers.size();
        for (int i = 0; i < size; i++) {
            WorksControllerManager.ControllerInfo info = controllers.valueAt(i);

            Bundle bundle = new Bundle();
            info.controller.onViewStateRestored(bundle);

            state.putParcelable(":worksController:" + controllers.keyAt(i), bundle);
        }
    }
}
