package com.mobilesolutionworks.android.app.test.works;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobilesolutionworks.android.app.WorksFragment;
import com.mobilesolutionworks.android.app.controller.WorksController;
import com.mobilesolutionworks.android.app.controller.WorksControllerManager;

/**
 * Created by yunarta on 9/3/17.
 */

public class EmptyWorksFragment extends WorksFragment {

    private WorksController mController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = getControllerManager().initController(0, null, new WorksControllerManager.ControllerCallbacks<WorksController>() {
            @Override
            public WorksController onCreateController(int id, Bundle bundle) {
                return new WorksController();
            }
        });
    }

    public WorksController getController() {
        return mController;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(com.mobilesolutionworks.android.app.test.R.layout.empty_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView id = (TextView) view.findViewById(com.mobilesolutionworks.android.app.test.R.id.textView);
        id.setText("empty");
    }
}
