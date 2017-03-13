package com.mobilesolutionworks.android.app.test.works;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.mobilesolutionworks.android.app.WorksDialogFragment;
import com.mobilesolutionworks.android.app.controller.WorksController;
import com.mobilesolutionworks.android.app.controller.WorksControllerManager;

/**
 * Created by yunarta on 12/3/17.
 */

public class WorksDialogFragmentImpl extends WorksDialogFragment {

    private WorksController mController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = getControllerManager().initController(0, null, new WorksControllerManager.ControllerCallbacks<WorksController>() {
            @Override
            public WorksController onCreateController(int id, Bundle args) {
                return new WorksController();
            }
        });
    }

    public WorksController getController() {
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
