package com.mobilesolutionworks.android.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Activity host for WorksController.
 * <p>
 * Created by yunarta on 19/11/15.
 */
public class WorksActivity extends AppCompatActivity {

//    private SparseArray<FragmentTrackInfo> mTrackInfoMap;

    /**
     * Controller manager.
     */
    private WorksControllerManager mController;

    public WorksActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WorksControllerManager.Loader loader = (WorksControllerManager.Loader) getSupportLoaderManager().initLoader(0, null, new WorksControllerManager.LoaderCallbacks(this));
        mController = loader.getController();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mController.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Get controller manager to create individual controller.
     *
     * @return controller manager.
     */
    public WorksControllerManager getControllerManager() {
        return mController;
    }

    @Override
    public void onResume() {
        super.onResume();
        mController.dispatchResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mController.dispatchPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mController.dispatchSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}
