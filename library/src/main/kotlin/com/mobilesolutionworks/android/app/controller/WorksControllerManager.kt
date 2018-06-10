package com.mobilesolutionworks.android.app.controller

import android.content.Context
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.util.SparseArray

//typealias ControllerCallbacks = (WorksControllerManager) -> WorksController

/**
 * Controller manager where WorksController will be created and persisted.
 *
 *
 * Created by yunarta on 18/11/15.
 */
class WorksControllerManager private constructor(context: Context) {

    /**
     * Collection of created controllers.
     */
    internal val controllers = SparseArray<WorksController>()

    /**
     * Lifecycle hook.
     */
    internal val lifecycleHook = WorksControllerLifecycleHook(this)

    fun getLifecycleHook(): WorksControllerLifecycleHook = lifecycleHook

    /**
     * Main scheduler.
     */
    /**
     * Get main scheduler.
     */
    internal val mainScheduler = WorksMainScheduler()

    /**
     * Get application context.
     */
    internal val context: Context = context.applicationContext

    /**
     * Dispatch pause, called by lifecycle hook.
     */
    internal fun dispatchPause() {
        mainScheduler.pause()
    }

    /**
     * Dispatch resume, called by lifecycle hook.
     */
    internal fun dispatchResume() {
        mainScheduler.resume()
    }


//    @FunctionalInterface
//    interface ControllerCallbacks<out D : WorksController> {
//
//        /**
//         * Called by implementation to create a Loader.
//         */
//        fun onCreateController(id: Int, args: Bundle?): D
//    }

    /**
     * Init a WorksController.
     *
     * @param id       a unique identifier for this loader.
     * *
     * @param args     optional arguments .
     * *
     * @param callback interface that controller manager will call to report about
     * *                 changes in the state of the loader.  Required.
     * *
     * @return returns controller implementation immediately, if one is already created before than it will be returned.
     */
    fun initWorksController(id: Int, args: Bundle?, callback: CreateCallback2<out WorksController>): WorksController {
        val controller: WorksController? = controllers.get(id)
        if (controller == null) {
            val newController = callback.create(this)
            newController.onCreate(args)

            controllers.put(id, newController)
            return newController
        } else {
            return controller
        }
    }

    fun <D : WorksController> initController(id: Int, args: Bundle?, callback: CreateCallback2<D>): D {
        val controller = initWorksController(id, args, callback)
        @Suppress("UNCHECKED_CAST")
        val test = controller as? D
        when {
            test != null -> return test
            else -> throw IllegalStateException("Controlled assigned with id $id is not created with this callback")
        }
    }

    /**
     * Get controller by id.

     * @param id the id that controller was created
     * *
     * @return associated controller is exist.
     */
    fun getController(id: Int): WorksController? {
        return controllers.get(id)
    }

    /**
     * Destroy associated controller.
     *
     *
     * This will call onDestroy of controller.
     */
    fun destroyController(id: Int) {
        val controller = controllers.get(id)
        if (controller != null) {
            controllers.remove(id)
            controller.onDestroy()
        }
    }

    /**
     * Loader implementation to create the WorksController.
     *
     *
     * This can be used when developer requires to use activities that is not subclass of WorksActivity.
     */
    class ControllerManager internal constructor(context: Context) : Loader<WorksControllerManager>(context) {

        val controller = WorksControllerManager(context)

        override fun onStartLoading() {
            super.onStartLoading()
            deliverResult(controller)
        }
    }

    /**
     * Loader callback implementation to create WorksController.
     *
     *
     * This can be used when developer requires to use activities that is not subclass of WorksActivity.
     */
    class ControllerManagerLoaderCallbacks(context: Context) : LoaderManager.LoaderCallbacks<WorksControllerManager> {

        private val context = context.applicationContext

        override fun onCreateLoader(id: Int, args: Bundle?): Loader<WorksControllerManager> {
            return ControllerManager(context).apply {
                startLoading()
            }
        }

        override fun onLoadFinished(loader: Loader<WorksControllerManager>, data: WorksControllerManager) {
        }

        override fun onLoaderReset(loader: Loader<WorksControllerManager>) {
            val controller = (loader as ControllerManager).controller
            controller.lifecycleHook.dispatchDestroy()
            controller.mainScheduler.release()
            controller.controllers.clear()
        }
    }

}
