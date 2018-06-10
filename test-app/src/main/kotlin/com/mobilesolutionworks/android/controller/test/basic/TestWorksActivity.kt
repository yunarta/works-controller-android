package com.mobilesolutionworks.android.controller.test.basic

import android.os.Bundle
import com.mobilesolutionworks.android.app.WorksActivity
import com.mobilesolutionworks.android.app.controller.CreateCallback2
import com.mobilesolutionworks.android.controller.test.GetController
import com.mobilesolutionworks.android.controller.test.R

/**
 * Created by yunarta on 12/3/17.
 */
open class TestWorksActivity : WorksActivity(), GetController<TestWorksController> {

    override var controller: TestWorksController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_host_activity)

        controller = controllerManager.initController(0, null, CreateCallback2 { TestWorksController(it) })

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, TestWorksFragment(), "root")
                    .commitNow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        controller = null
    }
}
