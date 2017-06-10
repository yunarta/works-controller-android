//package com.mobilesolutionworks.android.controller.test.basic;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.mobilesolutionworks.android.app.WorksFragment;
//import com.mobilesolutionworks.android.app.controller.WorksControllerManager;
//import com.mobilesolutionworks.android.controller.test.GetController;
//import com.mobilesolutionworks.android.controller.test.R;
//
///**
// * Created by yunarta on 9/3/17.
// */
//
//public class TestWorksFragment extends WorksFragment implements GetController<TestWorksController> {
//
//    private TestWorksController mController;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mController = getControllerManager().initController(0, null, new WorksControllerManager.ControllerCallbacks<TestWorksController>() {
//            @Override
//            public TestWorksController onCreateController(int id, Bundle args) {
//                return new TestWorksController();
//            }
//        });
//    }
//
//    public TestWorksController getController() {
//        return mController;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.empty_fragment, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        TextView id = (TextView) view.findViewById(R.id.textView);
//        id.setText("empty");
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mController = null;
//    }
//}
