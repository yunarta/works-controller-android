package com.mobilesolutionworks.android.app

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.mobilesolutionworks.android.app.controller.WorksControllerManager

/**
 * Activity host for WorksController.
 *
 * Created by yunarta on 19/11/15.
 */
open class WorksActivity : AppCompatActivity(), WorkControllerHost {

    var loader: WorksControllerManager.ControllerManager? = null

    /**
     * Get controller manager to create individual controller.
     * @return controller manager.
     */
    override val controllerManager: WorksControllerManager
        get() {
            return loader!!.controller
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loader = supportLoaderManager.initLoader(0, null, WorksControllerManager.ControllerManagerLoaderCallbacks(this)) as WorksControllerManager.ControllerManager
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        controllerManager.getLifecycleHook().onRestoreInstanceState(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        controllerManager.getLifecycleHook().onConfigurationChanged(newConfig)
    }

    public override fun onResume() {
        super.onResume()
        controllerManager.getLifecycleHook().dispatchResume()
    }

    public override fun onPause() {
        super.onPause()
        controllerManager.getLifecycleHook().dispatchPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        controllerManager.getLifecycleHook().dispatchSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }
}
