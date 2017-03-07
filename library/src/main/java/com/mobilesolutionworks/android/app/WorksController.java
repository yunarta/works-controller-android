package com.mobilesolutionworks.android.app;

import android.content.res.Configuration;
import android.os.Bundle;

/**
 * The main class of this library.
 * <p>
 * One of the main problem developing in Android is that whenever we have an orientation changed,
 * the whole Activity and Fragment structure may be recreated by Android.
 * <p>
 * This class will help you retain resource data that cannot be persisted by using saved state instance.
 * The class is persisted with the help of Android LoaderManager.
 * <p>
 * WorksController is a very basic skeleton for developer to extend and create functionality related to their requirement.
 * <p>
 * You might want to check {@link com.mobilesolutionworks.android.app.controller.TaskWorksController} to see a ready to
 * use implementation of WorksController that can be used to take care async operation termination.
 * <p>
 * Created by yunarta on 16/11/15.
 */
public class WorksController {

    public void onCreate(Bundle savedInstanceState) {

    }

    public void onPaused() {

    }

    public void onResume() {

    }

    public void onDestroy() {

    }

    public void onViewStateRestored(Bundle state) {

    }

    public void onSaveInstanceState(Bundle outState) {

    }

    public void onConfigurationChanged(Configuration config) {

    }
}
