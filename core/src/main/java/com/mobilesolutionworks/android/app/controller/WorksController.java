package com.mobilesolutionworks.android.app.controller;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * The main class of this library.
 * <p>
 * Created by yunarta on 16/11/15.
 */
public class WorksController {

    /**
     * Reference to manager.
     */
    private WorksControllerManager mManager;

    /**
     * Set controller manager, called by manager.
     *
     * @param manager the controller manager.
     */
    void setControllerManager(WorksControllerManager manager) {
        mManager = manager;
    }

    /**
     * Get application context from controller.
     *
     * @return application context.
     */
    @NonNull
    public Context getContext() {
        return mManager.getContext();
    }

    /**
     * Called when manager is creating this controller.
     * <p>
     * This is not related to Activity or Fragment onCreate.
     *
     * @param arguments arguments that is supplied in {@link WorksControllerManager#initController(int, Bundle, WorksControllerManager.ControllerCallbacks)}
     */
    public void onCreate(@Nullable Bundle arguments) {
        // Lifecycle event called when controller is created
    }

    /**
     * Called when host enters paused state.
     */
    protected void onPaused() {
        // Lifecycle event called when host is paused
    }

    /**
     * Called when host enters  resumed state
     */
    protected void onResume() {
        // Lifecycle event called when host is resumed
    }

    /**
     * Called when manager destroy the controller.
     */
    public void onDestroy() {
        // Lifecycle event called when controlled is destroyed
    }

    /**
     * Called when after host re-creation.
     *
     * @param state contains stated that you store in onSaveInstanceState.
     */
    protected void onRestoreInstanceState(@NonNull Bundle state) {
        // Lifecycle event called when host is view state is restored
    }

    /**
     * Called when host enter re-creation state.
     * <p>
     * In certain state, the controller might get release along with host
     * but usually what you store in state is still preserved after creation.
     *
     * @param outState bundle for storing information if required.
     */
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // Lifecycle event called when host is want to save instance state
    }

    /**
     * Called when host receive onConfigurationChanged.
     *
     * @param config new configuration.
     */
    public void onConfigurationChanged(@NonNull Configuration config) {
        // Lifecycle event called when host has configuration changed
    }

    /**
     * Executes the specified runnable after application enter resumed state.
     *
     * @param runnable runnable object.
     */
    public void runOnUIWhenIsReady(@NonNull final Runnable runnable) {
        mManager.getMainScheduler().runOnUIWhenIsReady(runnable);
    }

    /**
     * Executes the specified runnable immediately in main thread.
     *
     * @param runnable runnable object.
     */
    public void runOnMainThread(@NonNull Runnable runnable) {
        mManager.getMainScheduler().runOnMainThread(runnable);
    }

    /**
     * Executes the specified runnable delayed in main thread.
     *
     * @param runnable runnable object.
     * @param delay    time to delay in milliseconds.
     */
    public void runOnMainThreadDelayed(@NonNull Runnable runnable, long delay) {
        mManager.getMainScheduler().runOnMainThreadDelayed(runnable, delay);
    }
}
