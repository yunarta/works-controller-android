package com.mobilesolutionworks.android.controller.samples.ui.activity.main;

import android.content.Intent;

/**
 * Created by yunarta on 17/3/17.
 */

public class DemoItem {

    public final String title;

    public final String subtitle;

    public final Intent intent;

    DemoItem(String title, String subtitle, Intent intent) {
        this.title = title;
        this.subtitle = subtitle;
        this.intent = intent;
    }
}
