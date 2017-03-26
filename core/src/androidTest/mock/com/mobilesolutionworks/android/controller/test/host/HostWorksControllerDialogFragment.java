package com.mobilesolutionworks.android.controller.test.host;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.mobilesolutionworks.android.app.WorksDialogFragment;
import com.mobilesolutionworks.android.app.controller.HostWorksController;
import com.mobilesolutionworks.android.app.controller.WorksControllerManager;
import com.mobilesolutionworks.android.controller.test.GetController;

/**
 * Created by yunarta on 15/3/17.
 */

public class HostWorksControllerDialogFragment extends WorksDialogFragment implements GetController<HostWorksControllerDialogFragment.DialogFragmentControllerImpl> {

    private DialogFragmentControllerImpl mController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mController = HostWorksController.create(this, 0, null, new WorksControllerManager.ControllerCallbacks<DialogFragmentControllerImpl>() {
            @Override
            public DialogFragmentControllerImpl onCreateController(int id, Bundle args) {
                return new DialogFragmentControllerImpl();
            }
        });
    }

    @Override
    public DialogFragmentControllerImpl getController() {
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

    public static class DialogFragmentControllerImpl extends HostWorksController<HostWorksControllerDialogFragment> {

    }
}
