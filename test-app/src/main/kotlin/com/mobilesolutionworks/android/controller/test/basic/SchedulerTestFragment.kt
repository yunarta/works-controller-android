package com.mobilesolutionworks.android.controller.test.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobilesolutionworks.android.app.controller.CreateCallback2
import com.mobilesolutionworks.android.app.controller.WorksController
import com.mobilesolutionworks.android.controller.test.R

/**
 * Created by yunarta on 12/3/17.
 */

open class SchedulerTestFragment : SchedulerWorksFragment() {

    var controller: WorksController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = controllerManager.initController(0, null, CreateCallback2 { WorksController(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.scheduler_test_fragment, null, false)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        controller = null
    }
}
