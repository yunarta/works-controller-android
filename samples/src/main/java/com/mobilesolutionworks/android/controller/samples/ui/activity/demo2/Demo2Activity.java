package com.mobilesolutionworks.android.controller.samples.ui.activity.demo2;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.mobilesolutionworks.android.app.WorksActivity;
import com.mobilesolutionworks.android.controller.samples.R;

/**
 * Created by yunarta on 19/3/17.
 */

public class Demo2Activity extends WorksActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataBindingUtil.setContentView(this, R.layout.activity_demo2);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Demo2Fragment1())
                    .commit();
        }
    }
}