package com.mobilesolutionworks.android.controller.samples.ui.activity.demo1;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.mobilesolutionworks.android.app.WorksActivity;
import com.mobilesolutionworks.android.controller.HostWorksController;
import com.mobilesolutionworks.android.controller.samples.R;
import com.mobilesolutionworks.android.controller.samples.databinding.ActivityDemo1Binding;

/**
 * Created by yunarta on 19/3/17.
 */

public class Demo1Activity extends WorksActivity {

    private ActivityDemo1Binding mBinding;

    private Demo1ActivityController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mController = HostWorksController.create(this, 0, null, (id, args) -> new Demo1ActivityController());

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_demo1);
        mBinding.setActivityInstance(Integer.toHexString(System.identityHashCode(this)));
        mBinding.setControllerInstance(Integer.toHexString(System.identityHashCode(mController)));
    }

    public void postNumber(int counter) {
        mBinding.setCounter(String.valueOf(counter));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mController = null;
    }
}