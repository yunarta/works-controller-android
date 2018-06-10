package com.mobilesolutionworks.android.app.controller;

import com.mobilesolutionworks.android.app.WorkControllerHost;

public interface CreateCallback<C extends HostWorksController<H>, H extends WorkControllerHost> {

    C create(WorksControllerManager manager);
}