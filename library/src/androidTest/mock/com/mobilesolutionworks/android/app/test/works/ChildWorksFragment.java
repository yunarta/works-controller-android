package com.mobilesolutionworks.android.app.test.works;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yunarta on 9/3/17.
 */

public class ChildWorksFragment extends EmptyWorksFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView id = (TextView) view.findViewById(com.mobilesolutionworks.android.app.test.R.id.textView);
        id.setText("child");
    }
}
