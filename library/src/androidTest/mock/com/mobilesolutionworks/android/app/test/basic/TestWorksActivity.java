package com.mobilesolutionworks.android.app.test.basic;

import android.os.Bundle;

import com.mobilesolutionworks.android.app.WorksActivity;
import com.mobilesolutionworks.android.app.controller.WorksControllerManager;
import com.mobilesolutionworks.android.app.test.GetController;
import com.mobilesolutionworks.android.app.test.R;

/**
 * Created by yunarta on 12/3/17.
 */

public class TestWorksActivity extends WorksActivity implements GetController<TestWorksController> {

    private TestWorksController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_host_activity);

        mController = getControllerManager().initController(0, null, new WorksControllerManager.ControllerCallbacks<TestWorksController>() {
            @Override
            public TestWorksController onCreateController(int id, Bundle args) {
                return new TestWorksController();
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(com.mobilesolutionworks.android.app.test.R.id.fragment_container, new TestWorksFragment(), "root")
                    .commitNow();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mController = null;
    }

    public TestWorksController getController() {
        return mController;
    }
}
