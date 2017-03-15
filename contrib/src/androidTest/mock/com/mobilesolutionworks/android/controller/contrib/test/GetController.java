package com.mobilesolutionworks.android.controller.contrib.test;

import com.mobilesolutionworks.android.controller.HostWorksController;

/**
 * Created by yunarta on 12/3/17.
 */

public interface GetController<C extends HostWorksController> {

    C getController();
}
