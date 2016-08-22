package com.mobilesolutionworks.android.controller.samples.sample1;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mobilesolutionworks.android.app.WorksActivity;
import com.mobilesolutionworks.android.app.controller.TaskWorksController;
import com.mobilesolutionworks.android.controller.samples.R;

/**
 * A very simple controller that counts and post the result on the screen.
 * <p>
 * Created by yunarta on 23/8/16.
 */
public class CountingActivity extends WorksActivity {

    private CountingController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samples1);

        mController = getControllerManager().initController(0, null, new TaskWorksController.ControllerCallbacks<CountingController, CountingActivity>(this) {
            @Override
            public CountingController onCreateController(int id, Bundle args) {
                return new CountingController();
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mController.startCounting();
            }
        });
    }

    public void postNumber(Integer result) {
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(String.valueOf(result));
    }
}
