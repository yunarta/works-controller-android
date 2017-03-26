package com.mobilesolutionworks.android.controller.samples.ui.activity.demo2;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobilesolutionworks.android.app.WorksFragment;
import com.mobilesolutionworks.android.controller.HostWorksController;
import com.mobilesolutionworks.android.controller.samples.R;
import com.mobilesolutionworks.android.controller.samples.databinding.FragmentDemo2Fragment1Binding;

/**
 * Created by yunarta on 22/3/17.
 */

public class Demo2Fragment1 extends WorksFragment {

    private Demo2Fragment1Controller mController;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("[demo][demo2]", this + " onCreate()");

        mController = HostWorksController.create(this, 0, null, (id, args) -> new Demo2Fragment1Controller());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("[demo][demo2]", this + " onCreateView() is called");

        final FragmentDemo2Fragment1Binding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_demo2_fragment1, null, false);
        binding.setFragment(this);
        binding.setController(mController);
        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("[demo][demo2]", this + " onPause()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("[demo][demo2]", this + " onResume()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mController = null;
    }

    public void dispatchStartActivityForResult() {
        startActivityForResult(new Intent(getContext(), GivingResultActivity.class), 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Log.d("[demo][demo2]", this + " onActivityResult() is called");
            Log.d("[demo][demo2]", this + " fragment is in resumed state " + isResumed());
            final Runnable runnable = () -> {
                final FragmentDemo2Fragment1Binding binding = DataBindingUtil.findBinding(getView());
                if (binding != null) {
                    binding.ctrl2.setText(R.string.demo2_fragment1_withResult);
                }
            };

            if ("TO_CONTROLLER".equals(data.getAction())) {
                mController.runWhenUiIsReady(runnable);
            } else {
                runnable.run();
            }

            final boolean pushFragment = data.getBooleanExtra("push_fragment", true);
            if (pushFragment) {
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragment_container, new Demo2Fragment2())
                        .commit();
            }
        }
    }
}
