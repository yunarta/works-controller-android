package com.mobilesolutionworks.android.app.test.works;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by yunarta on 9/3/17.
 */

public class SchedulerTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mobilesolutionworks.android.app.test.R.layout.fragment_host_activity);
        findViewById(com.mobilesolutionworks.android.app.test.R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack("back-stack")
                        .replace(com.mobilesolutionworks.android.app.test.R.id.fragment_container, new EmptyWorksFragment(), "stack")
                        .commit();
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(com.mobilesolutionworks.android.app.test.R.id.fragment_container, new SchedulerTestFragment(), "root")
                    .commitNow();
        }
    }
}
