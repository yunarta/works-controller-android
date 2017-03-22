package com.mobilesolutionworks.android.controller.samples.ui.activity.main;

import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Created by yunarta on 17/3/17.
 */

public class DemoItem {

    public final String title;

    public final String subtitle;

    public final Intent intent;

    DemoItem(@NonNull String title, @NonNull String subtitle, @NonNull Intent intent) {
        this.title = title;
        this.subtitle = subtitle;
        this.intent = intent;
    }
}
