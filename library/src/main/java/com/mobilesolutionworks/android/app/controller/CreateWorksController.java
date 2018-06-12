package com.mobilesolutionworks.android.app.controller;

public interface CreateWorksController<C extends WorksController> {

    C create(WorksControllerManager manager);
}