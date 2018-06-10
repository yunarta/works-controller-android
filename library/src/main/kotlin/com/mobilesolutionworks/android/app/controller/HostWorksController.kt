package com.mobilesolutionworks.android.app.controller

import android.os.Bundle
import com.mobilesolutionworks.android.app.WorkControllerHost
import java.lang.ref.WeakReference

/**
 * Controller with host storing feature.
 *
 *
 * In actual development, developer will most likely use this class rather than WorksController.
 * As the host is updated  after device rotation, the controller can consider that the host is
 * always available when making update in [.runWhenUiIsReady] scope.
 *
 *
 * Created by yunarta on 15/3/17.
 */
abstract class HostWorksController<H : WorkControllerHost>(manager: WorksControllerManager) : WorksController(manager) {

    /**
     * Hold weak reference to host.
     */
    private var mHost = WeakReference<H>(null)

    val host: H?
        get() = mHost.get()

    /**
     * Update host, called by controller create callback.

     * @param host host for this controller.
     */
    fun updateHost(host: H) {
        mHost = WeakReference(host)
    }

    override fun onCreate(arguments: Bundle?) {
        super.onCreate(arguments)
        if (mHost.get() == null) {
            throw IllegalStateException("HostWorksController can only be created using HostWorksController.create function");
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mHost.clear()
    }

    companion object {

        fun <C : HostWorksController<H>, H : WorkControllerHost> create(host: H, id: Int, args: Bundle?, callback: CreateCallback<C, H>): C {
            // SuppressWarnings("squid:S1604")
            val controller: C = host.controllerManager.initController(id, args, CreateCallback2 {
                val controller: C = callback.create(it)
                controller.updateHost(host)
                controller
            })
            controller.updateHost(host)
            return controller
        }

//        inline fun <reified C : HostWorksController<H>, H : WorkControllerHost> create(host: H, id: Int, args: Bundle?, noinline callback: (WorksControllerManager) -> C): C {
//            // SuppressWarnings("squid:S1604")
//            val controller: C = host.controllerManager.initController(id, args, {
//                val controller: C = callback(it)
//                controller.updateHost(host)
//                controller
//            })
//            controller.updateHost(host)
//            return controller
//        }
    }
}