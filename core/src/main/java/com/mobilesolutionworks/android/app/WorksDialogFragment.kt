package com.mobilesolutionworks.android.app

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.DialogFragment

import com.mobilesolutionworks.android.app.controller.WorksControllerManager

/**
 * DialogFragment host for WorksController.
 *
 * Created by yunarta on 19/11/15.
 */
open class WorksDialogFragment : DialogFragment(), WorkControllerHost {

    var loader: WorksControllerManager.ControllerManager? = null

    /**
     * Get controller manager to create individual controller.
     * @return controller manager.
     */
    override val controllerManager: WorksControllerManager
        get() {
            return loader!!.controller
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loader = loaderManager.initLoader(0, null, WorksControllerManager.ControllerManagerLoaderCallbacks(context.applicationContext)) as WorksControllerManager.ControllerManager
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        controllerManager.getLifecycleHook().onRestoreInstanceState(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        controllerManager.getLifecycleHook().onConfigurationChanged(newConfig!!)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        controllerManager.getLifecycleHook().dispatchSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        controllerManager.getLifecycleHook().dispatchPause()
    }

    override fun onResume() {
        super.onResume()
        controllerManager.getLifecycleHook().dispatchResume()
    }
}