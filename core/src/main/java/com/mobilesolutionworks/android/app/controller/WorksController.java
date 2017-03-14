package com.mobilesolutionworks.android.app.controller;

import android.content.Context;
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
 * Created by yunarta on 16/11/15.
 */
public class WorksController {

    WorksControllerManager mManager;

    void setControllerManager(WorksControllerManager manager) {
        mManager = manager;
    }

    public Context getContext() {
        return mManager.getContext();
    }

    public void onCreate(Bundle arguments) {
        // Lifecycle event called when controller is created
    }

    public void onPaused() {
        // Lifecycle event called when host is paused
    }

    public void onResume() {
        // Lifecycle event called when host is resumed
    }

    public void onDestroy() {
        // Lifecycle event called when controlled is destroyed
    }

    public void onRestoreInstanceState(Bundle state) {
        // Lifecycle event called when host is view state is restored
    }

    public void onSaveInstanceState(Bundle outState) {
        // Lifecycle event called when host is want to save instance state
    }

    public void onConfigurationChanged(Configuration config) {
        // Lifecycle event called when host has configuration changed
    }

    public void runOnUIWhenIsReady(final Runnable runnable) {
        mManager.getMainScheduler().runOnUIWhenIsReady(runnable);
    }

    public void runOnMainThread(Runnable runnable) {
        mManager.getMainScheduler().runOnMainThread(runnable);
    }

    public void runOnMainThreadDelayed(Runnable runnable, long delay) {
        mManager.getMainScheduler().runOnMainThreadDelayed(runnable, delay);
    }
}
