package com.mobilesolutionworks.android.controller.test.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mobilesolutionworks.android.app.WorksFragment
import com.mobilesolutionworks.android.app.controller.CreateCallback2
import com.mobilesolutionworks.android.controller.test.GetController
import com.mobilesolutionworks.android.controller.test.R

/**
 * Created by yunarta on 9/3/17.
 */

class TestWorksFragment : WorksFragment(), GetController<TestWorksController> {

    override var controller: TestWorksController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = controllerManager.initController(0, null, CreateCallback2 { TestWorksController(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.empty_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = view.findViewById(R.id.textView) as TextView
        id.text = "empty"
    }

    override fun onDestroy() {
        super.onDestroy()
        controller = null
    }
}
