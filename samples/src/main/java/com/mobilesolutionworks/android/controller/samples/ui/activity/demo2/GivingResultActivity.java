package com.mobilesolutionworks.android.controller.samples.ui.activity.demo2;

import android.app.Activity;
import android.content.Intent;
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
        setResult(Activity.RESULT_OK, new Intent("TO_FRAGMENT"));
        finish();
    }

    public void dispatchSetResult2() {
        setResult(Activity.RESULT_OK, new Intent("TO_CONTROLLER"));
        finish();
    }

    public void dispatchSetResult3() {
        final Intent intent = new Intent("TO_FRAGMENT");
        intent.putExtra("push_fragment", false);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void dispatchSetResult4() {
        final Intent intent = new Intent("TO_CONTROLLER");
        intent.putExtra("push_fragment", false);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
