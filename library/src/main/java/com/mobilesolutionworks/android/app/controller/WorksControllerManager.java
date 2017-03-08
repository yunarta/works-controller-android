package com.mobilesolutionworks.android.app.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
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

    public interface ControllerCallbacks<D extends WorksController> {

        /**
         * Called by implementation to create a Loader.
         */
        D onCreateController(int id, Bundle bundle);
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
            newController.onCreate(args);

            mControllers.put(id, controller);
        }

        return (D) controller;
    }

    /**
     * Destroy associanted WorksController.
     * <p>
     * This will call onDestroy of WorksController.
     */
    public void destroyLoader(int id) {
        WorksController controller = mControllers.get(id);
        if (controller != null) {
            mControllers.remove(id);
            controller.onDestroy();
        }
    }

    /**
     * Loader implementation to create the WorksController.
     * <p>
     * This can be used when developer requires to use activities that is not subclass of WorksActivity.
     */
    public static class Loader extends android.support.v4.content.Loader<WorksControllerManager> {

        private WorksControllerManager mData;

        public Loader(Context context) {
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
            return new Loader(mContext);
        }

        @Override
        public void onLoadFinished(android.support.v4.content.Loader<WorksControllerManager> loader, WorksControllerManager data) {

        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<WorksControllerManager> loader) {
            WorksControllerManager controller = ((Loader) loader).getController();

            controller.getLifecycleHook().dispatchDestroy();
            controller.getMainScheduler().release();
        }
    }
}
