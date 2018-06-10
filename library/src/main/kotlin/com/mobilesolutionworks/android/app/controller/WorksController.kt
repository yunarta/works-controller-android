package com.mobilesolutionworks.android.app.controller

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.annotation.StringRes

/**
 * The main class of this library.
 *
 *
 * Created by yunarta on 16/11/15.
 */
open class WorksController(val mManager: WorksControllerManager) {

//    /**
//     * Reference to manager.
//     */
//    private var mManager: WorksControllerManager? = null

//    /**
//     * Set controller manager, called by manager.
//
//     * @param manager the controller manager.
//     */
//    internal fun setControllerManager(manager: WorksControllerManager) {
//        mManager = manager
//    }

    /**
     * Get application context from controller.

     * @return application context.
     */
    val context: Context
        get() = mManager.context

    /**
     * Get string from context
     */
    fun getString(@StringRes id: Int): String {
        return mManager.context.getString(id)
    }

    /**
     * Called when manager is creating this controller.
     *
     *
     * This is not related to Activity or Fragment onCreate.

     * @param arguments arguments that is supplied in [WorksControllerManager.initController]
     */
    open fun onCreate(arguments: Bundle?) {
        // Lifecycle event called when controller is created
    }

    internal fun dispatchOnPaused() = onPaused()

    /**
     * Called when host enters paused state.
     */
    protected fun onPaused() {
        // Lifecycle event called when host is paused
    }

    /**
     * Called when host enters  resumed state
     */
    protected fun onResume() {
        // Lifecycle event called when host is resumed
    }

    internal fun dispatchOnResume() = onResume()

    /**
     * Called when manager destroy the controller.
     */
    open fun onDestroy() {
        // Lifecycle event called when controlled is destroyed
    }

    /**
     * Called when after host re-creation.

     * @param state contains stated that you store in onSaveInstanceState.
     */
    open fun onRestoreInstanceState(state: Bundle) {
        // Lifecycle event called when host is view state is restored
    }

    internal fun dispatchOnRestoreInstanceState(state: Bundle) = onRestoreInstanceState(state)

    /**
     * Called when host enter re-creation state.
     *
     *
     * In certain state, the controller might get release along with host
     * but usually what you store in state is still preserved after creation.

     * @param outState bundle for storing information if required.
     */
    open fun onSaveInstanceState(outState: Bundle) {
        // Lifecycle event called when host is want to save instance state
    }

    internal fun dispatchOnSaveInstanceState(outState: Bundle) = onSaveInstanceState(outState)

    /**
     * Called when host receive onConfigurationChanged.

     * @param config new configuration.
     */
    open fun onConfigurationChanged(config: Configuration) {
        // Lifecycle event called when host has configuration changed
    }

    /**
     * Executes the specified runnable after application enter resumed state.

     * @param runnable runnable object.
     */
    fun runWhenUiIsReady(runnable: Runnable) {
        mManager.mainScheduler.runWhenUiIsReady(runnable)
    }

    /**
     * Executes the specified runnable immediately in main thread.

     * @param runnable runnable object.
     */
    fun runOnMainThread(runnable: Runnable) {
        mManager.mainScheduler.runOnMainThread(runnable)
    }

    /**
     * Executes the specified runnable delayed in main thread.

     * @param runnable runnable object.
     * *
     * @param delay    time to delay in milliseconds.
     */
    fun runOnMainThreadDelayed(runnable: Runnable, delay: Long) {
        mManager.mainScheduler.runOnMainThreadDelayed(runnable, delay)
    }
}
