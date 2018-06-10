package com.mobilesolutionworks.android.controller.test.host

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.mobilesolutionworks.android.app.WorksDialogFragment
import com.mobilesolutionworks.android.app.controller.CreateCallback
import com.mobilesolutionworks.android.app.controller.HostWorksController
import com.mobilesolutionworks.android.app.controller.WorksControllerManager
import com.mobilesolutionworks.android.controller.test.GetController

/**
 * Created by yunarta on 15/3/17.
 */

class HostWorksControllerDialogFragment : WorksDialogFragment(), GetController<HostWorksControllerDialogFragment.DialogFragmentControllerImpl> {

    override var controller: DialogFragmentControllerImpl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller = HostWorksController.create(this, 0, null, CreateCallback {
            DialogFragmentControllerImpl(it)
        })
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

    class DialogFragmentControllerImpl(manager: WorksControllerManager) : HostWorksController<HostWorksControllerDialogFragment>(manager)
}
