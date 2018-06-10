package com.mobilesolutionworks.android.app

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.mobilesolutionworks.android.app.controller.WorksControllerManager

/**
 * Fragment host for WorksController.
 *
 * Created by yunarta on 19/11/15.
 */
open class WorksFragment : Fragment(), WorkControllerHost {

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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        controllerManager.lifecycleHook.onConfigurationChanged(newConfig)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerManager.lifecycleHook.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        controllerManager.lifecycleHook.dispatchSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        controllerManager.lifecycleHook.dispatchPause()
    }

    override fun onResume() {
        super.onResume()
        controllerManager.lifecycleHook.dispatchResume()
    }
}
