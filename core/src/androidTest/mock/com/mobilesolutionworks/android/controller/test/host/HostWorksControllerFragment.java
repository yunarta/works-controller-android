//package com.mobilesolutionworks.android.controller.test.host;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//
//import com.mobilesolutionworks.android.app.WorksFragment;
//import com.mobilesolutionworks.android.app.controller.HostWorksController;
//import com.mobilesolutionworks.android.app.controller.WorksControllerManager;
//import com.mobilesolutionworks.android.controller.test.GetController;
//
//import org.jetbrains.annotations.NotNull;
//
///**
// * Created by yunarta on 15/3/17.
// */
//
//public class HostWorksControllerFragment extends WorksFragment implements GetController<HostWorksControllerFragment.FragmentControllerImpl> {
//
//    private FragmentControllerImpl mController;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        mController = HostWorksController.create(this, 0, null, new WorksControllerManager.ControllerCallbacks<FragmentControllerImpl>() {
//            @Override
//            public FragmentControllerImpl onCreateController(int id, Bundle args) {
//                return new FragmentControllerImpl();
//            }
//        });
//    }
//
//    @Override
//    public FragmentControllerImpl getController() {
//        return mController;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mController = null;
//    }
//
//    public static class FragmentControllerImpl extends HostWorksController<HostWorksControllerFragment> {
//
//        public FragmentControllerImpl(@NotNull WorksControllerManager manager) {
//            super(manager);
//        }
//    }
//}
