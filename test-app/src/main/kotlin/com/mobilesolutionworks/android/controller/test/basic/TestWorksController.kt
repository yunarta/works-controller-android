package com.mobilesolutionworks.android.controller.test.basic

import android.content.res.Configuration

import com.mobilesolutionworks.android.app.controller.WorksController
import com.mobilesolutionworks.android.app.controller.WorksControllerManager

/**
 * Created by yunarta on 12/3/17.
 */

class TestWorksController(manager: WorksControllerManager) : WorksController(manager) {

    var configChangesCount = 0

    override fun onConfigurationChanged(config: Configuration) {
        super.onConfigurationChanged(config)
        configChangesCount++
    }
}
