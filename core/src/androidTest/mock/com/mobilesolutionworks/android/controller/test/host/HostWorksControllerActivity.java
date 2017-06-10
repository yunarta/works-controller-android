//package com.mobilesolutionworks.android.controller.test.host;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//
//import com.mobilesolutionworks.android.app.WorksActivity;
//import com.mobilesolutionworks.android.app.controller.HostWorksController;
//import com.mobilesolutionworks.android.app.controller.WorksControllerManager;
//import com.mobilesolutionworks.android.controller.test.GetController;
//import com.mobilesolutionworks.android.controller.test.R;
//
///**
// * Created by yunarta on 15/3/17.
// */
//
//public class HostWorksControllerActivity extends WorksActivity implements GetController<HostWorksControllerActivity.ActivityControllerImpl> {
//
//    private ActivityControllerImpl mController;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_host_activity);
//
//        mController = HostWorksController.create(this, 0, null, new WorksControllerManager.ControllerCallbacks<ActivityControllerImpl>() {
//            @Override
//            public ActivityControllerImpl onCreateController(int id, Bundle args) {
//                return new ActivityControllerImpl();
//            }
//        });
//
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container, new HostWorksControllerFragment(), "root")
//                    .commitNow();
//        }
//    }
//
//    @Override
//    public ActivityControllerImpl getController() {
//        return mController;
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mController = null;
//    }
//
//    public static class ActivityControllerImpl extends HostWorksController<HostWorksControllerActivity> {
//
//    }
//}
