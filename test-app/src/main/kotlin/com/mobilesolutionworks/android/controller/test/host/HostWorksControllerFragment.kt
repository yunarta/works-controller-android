package com.mobilesolutionworks.android.controller.test.host

import android.os.Bundle

import com.mobilesolutionworks.android.app.WorksFragment
import com.mobilesolutionworks.android.app.controller.CreateCallback
import com.mobilesolutionworks.android.app.controller.HostWorksController
import com.mobilesolutionworks.android.app.controller.WorksControllerManager
import com.mobilesolutionworks.android.controller.test.GetController

/**
 * Created by yunarta on 15/3/17.
 */

class HostWorksControllerFragment : WorksFragment(), GetController<HostWorksControllerFragment.FragmentControllerImpl> {

    override var controller: FragmentControllerImpl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller = HostWorksController.create(this, 0, null, CreateCallback {
            FragmentControllerImpl(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        controller = null
    }

    class FragmentControllerImpl(manager: WorksControllerManager) : HostWorksController<HostWorksControllerFragment>(manager)
}
