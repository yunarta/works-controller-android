package com.mobilesolutionworks.android.app.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yunarta on 9/3/17.
 */

public class RootFragment extends EmptyFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getChildFragmentManager().beginTransaction()
                    .add(new ChildFragment(), "child")
                    .commit();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView id = (TextView) view.findViewById(R.id.textView);
        id.setText("child");
    }

}
