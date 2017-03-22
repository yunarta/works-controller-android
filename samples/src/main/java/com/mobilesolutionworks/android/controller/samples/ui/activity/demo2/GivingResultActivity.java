package com.mobilesolutionworks.android.controller.samples.ui.activity.demo2;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mobilesolutionworks.android.controller.samples.R;
import com.mobilesolutionworks.android.controller.samples.databinding.ActivityGivingResultBinding;

/**
 * Created by yunarta on 22/3/17.
 */

public class GivingResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityGivingResultBinding binder;
        binder = DataBindingUtil.setContentView(this, R.layout.activity_giving_result);
        binder.setActivity(this);
    }

    public void dispatchSetResult() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
