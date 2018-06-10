package com.mobilesolutionworks.android.controller.test

import com.mobilesolutionworks.android.app.controller.WorksController

/**
 * Created by yunarta on 12/3/17.
 */

interface GetController<out C : WorksController> {

    val controller: C?
}
