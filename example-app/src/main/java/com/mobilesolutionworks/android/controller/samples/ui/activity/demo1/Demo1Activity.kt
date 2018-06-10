package com.mobilesolutionworks.android.controller.samples.ui.activity.demo1

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.mobilesolutionworks.android.app.WorksActivity
import com.mobilesolutionworks.android.app.controller.HostWorksController
import com.mobilesolutionworks.android.controller.samples.databinding.ActivityDemo1Binding

/**
 * Created by yunarta on 8/6/17.
 */

class Demo1Activity : WorksActivity() {

    private var mBinding: ActivityDemo1Binding? = null

    private var mController: Demo1ActivityController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mController = HostWorksController.create(this, 0, null, callback = ::Demo1ActivityController)


//        mController = HostWorksController.create(this, 0, null, callback = object : WorksControllerManager.ControllerCallbacks<Demo1ActivityController> {
//            override fun onCreateController(id: Int, args: Bundle?): Demo1ActivityController = Demo1ActivityController()
//        })

        mBinding = DataBindingUtil.setContentView<ActivityDemo1Binding>(this, R.layout.activity_demo1)
        mBinding!!.activityInstance = Integer.toHexString(System.identityHashCode(this))
        mBinding!!.controllerInstance = Integer.toHexString(System.identityHashCode(mController))
    }

    fun postNumber(counter: Int) {
        mBinding!!.counter = counter.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        mController = null
    }
}