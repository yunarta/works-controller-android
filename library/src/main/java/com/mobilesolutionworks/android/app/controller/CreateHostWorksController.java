package com.mobilesolutionworks.android.app.controller;

import com.mobilesolutionworks.android.app.WorkControllerHost;

public interface CreateHostWorksController<C extends HostWorksController<H>, H extends WorkControllerHost> {

    C create(WorksControllerManager manager);
}