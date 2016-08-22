package com.mobilesolutionworks.android.app;

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

    SparseArray<ControllerInfo> mControllers;

    public WorksControllerManager() {
        mControllers = new SparseArray<>();
    }

    /**
     * Hook host dispatch pause to controller.
     * <p>
     * This method must be called by host.
     */
    public void dispatchPause() {
        int size = mControllers.size();
        for (int i = 0; i < size; i++) {
            mControllers.valueAt(i).controller.onPaused();
        }
    }

    /**
     * Hook host dispatch resume to controller.
     * <p>
     * This method must be called by host.
     */
    public void dispatchResume() {
        int size = mControllers.size();
        for (int i = 0; i < size; i++) {
            mControllers.valueAt(i).controller.onResume();
        }
    }

    /**
     * Hook host dispatch destroy to controller.
     * <p>
     * This method must be called by host.
     */
    private void dispatchDestroy() {
        int size = mControllers.size();
        for (int i = 0; i < size; i++) {
            mControllers.valueAt(i).controller.onDestroy();
        }
        mControllers.clear();
    }

    /**
     * Hook host dispatch restore instance to controller.
     * <p>
     * This method must be called by host.
     */
    public void onRestoreInstanceState(Bundle state) {
        if (state != null) {
            int size = mControllers.size();
            for (int i = 0; i < size; i++) {
                Bundle bundle = state.getParcelable(":worksController:" + mControllers.keyAt(i));
                mControllers.valueAt(i).controller.onViewStateRestored(bundle);
            }
        }
    }

    /**
     * Hook host dispatch save instance state instance to controller.
     * <p>
     * This method must be called by host.
     */
    public void dispatchSaveInstanceState(Bundle state) {
        int size = mControllers.size();
        for (int i = 0; i < size; i++) {
            ControllerInfo info = mControllers.valueAt(i);

            Bundle bundle = new Bundle();
            info.controller.onViewStateRestored(bundle);

            state.putParcelable(":worksController:" + mControllers.keyAt(i), bundle);
        }
    }

    public interface ControllerCallbacks<D extends WorksController> {

        /**
         * Called by implementation to create a Loader.
         */
        D onCreateController(int id, Bundle bundle);

        /**
         * Called when loading is finished.
         */
        void onLoadFinished(int id, Bundle bundle, D controller);
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
        ControllerInfo<D> info = mControllers.get(id);
        if (info == null) {
            info = new ControllerInfo<>(callback);

            D newController = callback.onCreateController(id, args);
            info.controller = newController;

            callback.onLoadFinished(id, args, newController);
            newController.onCreate(args);

            mControllers.put(id, info);
        } else {
            info.callback = callback;
            info.callback.onLoadFinished(id, args, info.controller);
        }

        return (D) info.controller;
    }

    /**
     * Destroy associanted WorksController.
     * <p>
     * This will call onDestroy of WorksController.
     */
    public void destroyLoader(int id) {
        ControllerInfo info = mControllers.get(id);
        if (info != null) {
            info.controller.onDestroy();
            mControllers.remove(id);
        }
    }

    private class ControllerInfo<D extends WorksController> {

        D controller;

        ControllerCallbacks<D> callback;

        public ControllerInfo(ControllerCallbacks<D> callback) {
            this.callback = callback;
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
            Loader l = (Loader) loader;
            l.getController().dispatchDestroy();
        }
    }
}
