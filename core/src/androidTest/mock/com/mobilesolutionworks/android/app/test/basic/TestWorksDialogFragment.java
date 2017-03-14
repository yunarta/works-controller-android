package com.mobilesolutionworks.android.app.test.basic;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.mobilesolutionworks.android.app.WorksDialogFragment;
import com.mobilesolutionworks.android.app.controller.WorksControllerManager;
import com.mobilesolutionworks.android.app.test.GetController;

/**
 * Created by yunarta on 9/3/17.
 */

public class TestWorksDialogFragment extends WorksDialogFragment implements GetController<TestWorksController> {

    private TestWorksController mController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = getControllerManager().initController(0, null, new WorksControllerManager.ControllerCallbacks<TestWorksController>() {
            @Override
            public TestWorksController onCreateController(int id, Bundle args) {
                return new TestWorksController();
            }
        });
    }

    public TestWorksController getController() {
        return mController;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setMessage("Example")
                .setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .create();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mController = null;
    }
}
