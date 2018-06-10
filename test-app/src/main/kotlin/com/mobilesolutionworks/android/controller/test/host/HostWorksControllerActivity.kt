package com.mobilesolutionworks.android.controller.test.host

import android.os.Bundle
import com.mobilesolutionworks.android.app.WorksActivity
import com.mobilesolutionworks.android.app.controller.CreateCallback
import com.mobilesolutionworks.android.app.controller.HostWorksController
import com.mobilesolutionworks.android.app.controller.WorksControllerManager
import com.mobilesolutionworks.android.controller.test.GetController
import com.mobilesolutionworks.android.controller.test.R

/**
 * Created by yunarta on 15/3/17.
 */

open class HostWorksControllerActivity : WorksActivity(), GetController<HostWorksControllerActivity.ActivityControllerImpl> {

    override var controller: ActivityControllerImpl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_host_activity)

        controller = HostWorksController.create(this, 0, null, CreateCallback {
            ActivityControllerImpl(it)
        })

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HostWorksControllerFragment(), "root")
                    .commitNow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        controller = null
    }

    class ActivityControllerImpl(manager: WorksControllerManager) : HostWorksController<HostWorksControllerActivity>(manager)
}