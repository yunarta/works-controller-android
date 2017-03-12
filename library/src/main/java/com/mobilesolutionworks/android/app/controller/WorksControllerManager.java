package com.mobilesolutionworks.android.app.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.util.SparseArray;

/**
 * Controller manager where WorksController will be created and persisted.
 * <p>
 * Created by yunarta on 18/11/15.
 */
public class WorksControllerManager {

    private final SparseArray<WorksController> mControllers;

    private final WorksControllerLifecycleHook mLifecycleHook;

    private final WorksMainScheduler mMainScheduler;

    public WorksControllerManager() {
        mControllers = new SparseArray<>();
        mLifecycleHook = new WorksControllerLifecycleHook(this);

        mMainScheduler = new WorksMainScheduler();
    }

    public WorksControllerLifecycleHook getLifecycleHook() {
        return mLifecycleHook;
    }

    SparseArray<WorksController> getControllers() {
        return mControllers;
    }

    WorksMainScheduler getMainScheduler() {
        return mMainScheduler;
    }

    void dispatchPause() {
        mMainScheduler.pause();
    }

    void dispatchResume() {
        mMainScheduler.resume();
    }

    @FunctionalInterface
    public interface ControllerCallbacks<D extends WorksController> {

        /**
         * Called by implementation to create a Loader.
         */
        D onCreateController(int id, Bundle args);
    }

    /**
     * Init a WorksController.
     *
     * @param id       A unique identifier for this loader.
     * @param args     Optional arguments or a Activity/Fragment saved instance state.
     * @param callback Interface the ControllerManager will call to report about
     *                 changes in the state of the loader.  Required.
     * @return returns WorksController implementation immediately, if one is already created before than it will be returned.
     */
    public <D extends WorksController> D initController(int id, Bundle args, ControllerCallbacks<D> callback) {
        WorksController controller = mControllers.get(id);
        if (controller == null) {
            D newController = callback.onCreateController(id, args);
            newController.setControllerManager(this);
            newController.onCreate(args);

            mControllers.put(id, newController);
            controller = newController;
        }

        return (D) controller;
    }

    /**
     * Destroy associanted WorksController.
     * <p>
     * This will call onDestroy of WorksController.
     */
    public void destroyController(int id) {
        WorksController controller = mControllers.get(id);
        if (controller != null) {
            mControllers.remove(id);
            controller.onDestroy();
        }
    }

    private void release() {
        getLifecycleHook().dispatchDestroy();
        getMainScheduler().release();
        mControllers.clear();

    }

    /**
     * Loader implementation to create the WorksController.
     * <p>
     * This can be used when developer requires to use activities that is not subclass of WorksActivity.
     */
    public static class InternalLoader extends android.support.v4.content.Loader<WorksControllerManager> {

        private WorksControllerManager mData;

        public InternalLoader(Context context) {
            super(context);
            mData = new WorksControllerManager();
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            deliverResult(mData);
        }

        public WorksControllerManager getController() {
            return mData;
        }
    }

    /**
     * Loader callback implementation to create WorksController.
     * <p>
     * This can be used when developer requires to use activities that is not subclass of WorksActivity.
     */
    public static class LoaderCallbacks implements LoaderManager.LoaderCallbacks<WorksControllerManager> {

        private Context mContext;

        public LoaderCallbacks(Context context) {
            mContext = context.getApplicationContext();
        }

        @Override
        public android.support.v4.content.Loader<WorksControllerManager> onCreateLoader(int id, Bundle args) {
            return new InternalLoader(mContext);
        }

        @Override
        public void onLoadFinished(Loader<WorksControllerManager> loader, WorksControllerManager data) {
            // we dont have to take care this callback
        }

        @Override
        public void onLoaderReset(Loader<WorksControllerManager> loader) {
            WorksControllerManager controller = ((InternalLoader) loader).getController();
            controller.release();
        }
    }
}
