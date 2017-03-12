package com.mobilesolutionworks.android.app.test.works;

import android.os.Bundle;

import com.mobilesolutionworks.android.app.WorksActivity;
import com.mobilesolutionworks.android.app.controller.WorksController;
import com.mobilesolutionworks.android.app.controller.WorksControllerManager;

/**
 * Created by yunarta on 12/3/17.
 */

public class WorksActivityImpl extends WorksActivity {

    private WorksController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mController = getControllerManager().initController(0, null, new WorksControllerManager.ControllerCallbacks<WorksController>() {
            @Override
            public WorksController onCreateController(int id, Bundle args) {
                return new WorksController();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mController = null;
    }

    public WorksController getController() {
        return mController;
    }
}
