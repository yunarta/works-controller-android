package com.mobilesolutionworks.android.controller.samples.ui.activity.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.mobilesolutionworks.android.app.WorksActivity;
import com.mobilesolutionworks.android.app.controller.HostWorksController;
import com.mobilesolutionworks.android.controller.samples.R;
import com.mobilesolutionworks.android.controller.samples.databinding.ActivityMainBinding;

/**
 * Created by yunarta on 17/3/17.
 */

public class MainActivity extends WorksActivity {

    private MainActivityController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = HostWorksController.create(this, 0, null, (id, args) -> new MainActivityController());

        ActivityMainBinding binding;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(mController.getAdapter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mController = null;
    }
}
