package com.mobilesolutionworks.android.app.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.util.SparseArray;

import java.lang.ref.WeakReference;

/**
 * Controller manager where WorksController will be created and persisted.
 * <p>
 * Created by yunarta on 18/11/15.
 */
public class WorksControllerManager {

    /**
     * Collection of created controllers.
     */
    private final SparseArray<WorksController> mControllers;

    /**
     * Lifecycle hook.
     */
    private final WorksControllerLifecycleHook mLifecycleHook;

    /**
     * Main scheduler.
     */
    private final WorksMainScheduler mMainScheduler;

    /**
     * Application context.
     */
    private Context mContext;

    /**
     * Create instance of controller manager.
     */
    protected WorksControllerManager() {
        mControllers = new SparseArray<>();
        mLifecycleHook = new WorksControllerLifecycleHook(this);

        mMainScheduler = new WorksMainScheduler();

    }

    /**
     * Get lifecycle hook for the host to dispatch their lifecycle event.
     *
     * @return lifecycle hook instance.
     */
    public WorksControllerLifecycleHook getLifecycleHook() {
        return mLifecycleHook;
    }

    /**
     * Get controller.
     */
    SparseArray<WorksController> getControllers() {
        return mControllers;
    }

    /**
     * Get main scheduler.
     */
    WorksMainScheduler getMainScheduler() {
        return mMainScheduler;
    }

    /**
     * Dispatch pause, called by lifecycle hook.
     */
    void dispatchPause() {
        mMainScheduler.pause();
    }

    /**
     * Dispatch resume, called by lifecycle hook.
     */
    void dispatchResume() {
        mMainScheduler.resume();
    }

    /**
     * Update context when the host is recreated.
     */
    void updateContext(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * Get application context.
     */
    Context getContext() {
        return mContext;
    }

    @FunctionalInterface
    public interface ControllerCallbacks<D extends WorksController> {

        /**
         * Called by implementation to create a Loader.
         */
        D onCreateController(int id, @Nullable Bundle args);
    }

    /**
     * Init a WorksController.
     *
     * @param id       a unique identifier for this loader.
     * @param args     optional arguments .
     * @param callback interface that controller manager will call to report about
     *                 changes in the state of the loader.  Required.
     * @return returns controller implementation immediately, if one is already created before than it will be returned.
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
     * Get controller by id.
     *
     * @param id the id that controller was created
     * @return associated controller is exist.
     */
    @Nullable
    public WorksController getController(int id) {
        return mControllers.get(id);
    }

    /**
     * Destroy associated controller.
     * <p>
     * This will call onDestroy of controller.
     */
    public void destroyController(int id) {
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
    public static class ControllerManager extends android.support.v4.content.Loader<WorksControllerManager> {

        private WorksControllerManager mData;

        ControllerManager(Context context) {
            super(context);
            mData = new WorksControllerManager();
            mData.updateContext(super.getContext());
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
    public static class ControllerManagerLoaderCallbacks implements LoaderManager.LoaderCallbacks<WorksControllerManager> {

        private WeakReference<Context> mContext;

        public ControllerManagerLoaderCallbacks(Context context) {
            mContext = new WeakReference<>(context.getApplicationContext());
        }

        @Override
        public android.support.v4.content.Loader<WorksControllerManager> onCreateLoader(int id, Bundle args) {
            return new ControllerManager(mContext.get());
        }

        @Override
        public void onLoadFinished(android.support.v4.content.Loader loader, WorksControllerManager data) {
            ((ControllerManager) loader).getController().updateContext(mContext.get());
        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader loader) {
            WorksControllerManager controller = ((ControllerManager) loader).getController();
            controller.getLifecycleHook().dispatchDestroy();
            controller.getMainScheduler().release();
            controller.mControllers.clear();
        }
    }
}
