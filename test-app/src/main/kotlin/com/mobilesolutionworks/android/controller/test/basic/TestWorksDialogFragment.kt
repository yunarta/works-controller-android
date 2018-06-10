package com.mobilesolutionworks.android.controller.test.basic

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.mobilesolutionworks.android.app.WorksDialogFragment
import com.mobilesolutionworks.android.app.controller.CreateCallback2
import com.mobilesolutionworks.android.controller.test.GetController

/**
 * Created by yunarta on 9/3/17.
 */

class TestWorksDialogFragment : WorksDialogFragment(), GetController<TestWorksController> {

    override var controller: TestWorksController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = controllerManager.initController(0, null, CreateCallback2 { TestWorksController(it) })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!)
              .setMessage("Example")
              .setPositiveButton("CLOSE") { dialog, which -> dismiss() }
              .create()
    }

    override fun onDestroy() {
        super.onDestroy()
        controller = null
    }
}
