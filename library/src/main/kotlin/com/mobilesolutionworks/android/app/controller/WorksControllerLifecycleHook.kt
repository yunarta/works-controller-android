package com.mobilesolutionworks.android.app.controller

import android.content.res.Configuration
import android.os.Bundle

/**
 * Exposed hook for host to pass their lifecycle.
 *
 *
 * Created by yunarta on 8/3/17.
 */
class WorksControllerLifecycleHook internal constructor(private val mManager: WorksControllerManager) {

    /**
     * Hook host dispatch pause to controller.
     *
     *
     * This method must be called by host.
     */
    fun dispatchPause() {
        mManager.dispatchPause()
        val controllers = mManager.controllers
        val size = controllers.size()
        for (i in 0..size - 1) {
            controllers.valueAt(i).dispatchOnPaused()
        }
    }

    /**
     * Hook host dispatch resume to controller.
     *
     *
     * This method must be called by host.
     */
    fun dispatchResume() {
        mManager.dispatchResume()
        val controllers = mManager.controllers
        val size = controllers.size()
        for (i in 0..size - 1) {
            controllers.valueAt(i).dispatchOnResume()
        }
    }

    /**
     * Hook host dispatch destroy to controller.
     *
     *
     * This method must be called by host.
     */
    internal fun dispatchDestroy() {
        val controllers = mManager.controllers
        val size = controllers.size()
        for (i in 0..size - 1) {
            controllers.valueAt(i).onDestroy()
        }
        controllers.clear()
    }

    fun onConfigurationChanged(config: Configuration) {
        val controllers = mManager.controllers
        val size = controllers.size()
        for (i in 0..size - 1) {
            controllers.valueAt(i).onConfigurationChanged(config)
        }
    }

    /**
     * Hook host dispatch restore instance to controller.
     *
     *
     * This method must be called by host.
     */
    fun onRestoreInstanceState(state: Bundle?) {
        if (state != null) {
            val controllers = mManager.controllers
            val size = controllers.size()
            for (i in 0..size - 1) {
                val bundle = state.getParcelable<Bundle>(":worksController:" + controllers.keyAt(i))
                if (bundle != null) {
                    controllers.valueAt(i).dispatchOnRestoreInstanceState(bundle)
                }
            }
        }
    }

    /**
     * Hook host dispatch save instance state instance to controller.
     *
     *
     * This method must be called by host.
     */
    fun dispatchSaveInstanceState(state: Bundle) {
        val controllers = mManager.controllers
        val size = controllers.size()
        for (i in 0..size - 1) {
            val controller = controllers.valueAt(i)

            val bundle = Bundle()
            controller.dispatchOnSaveInstanceState(bundle)

            state.putParcelable(":worksController:" + controllers.keyAt(i), bundle)
        }
    }
}