//package com.mobilesolutionworks.android.controller.test.basic;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.mobilesolutionworks.android.app.controller.WorksController;
//import com.mobilesolutionworks.android.app.controller.WorksControllerManager;
//import com.mobilesolutionworks.android.controller.test.R;
//
///**
// * Created by yunarta on 12/3/17.
// */
//
//public class SchedulerTestFragment extends SchedulerWorksFragment {
//
//    private WorksController mController;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mController = getControllerManager().initController(0, null, new WorksControllerManager.ControllerCallbacks<WorksController>() {
//            @Override
//            public WorksController onCreateController(int id, Bundle args) {
//                return new WorksController();
//            }
//        });
//    }
//
//    public WorksController getController() {
//        return mController;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.scheduler_test_fragment, null, false);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mController = null;
//    }
//}
