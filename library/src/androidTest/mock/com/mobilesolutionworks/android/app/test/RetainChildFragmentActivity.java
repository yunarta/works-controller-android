package com.mobilesolutionworks.android.app.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by yunarta on 9/3/17.
 */

public class RetainChildFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retain_child_fragment_activity);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack("back-stack")
                        .replace(R.id.fragment_container, new EmptyFragment(), "stack")
                        .commit();
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RootFragment(), "root")
                    .commitNow();
        }
    }
}
