package com.mobilesolutionworks.android.app.controller;

import android.os.Bundle;

import com.mobilesolutionworks.android.app.WorksController;
import com.mobilesolutionworks.android.app.WorksControllerManager;

import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;

/**
 * Implementation of WorksController that specially created to take care async operation
 * termination.
 * <p>
 * The class works by using Bolts {@link Task} to take care of the termination.
 * The premise of the task operation termination is that we wanted to have the termination to be executed
 * only when the Host had its view ready and entered Resumed stated.
 * <p>
 * Created by yunarta on 28/6/16.
 */
public class TaskWorksController<Host> extends WorksController {

    private boolean mIsPaused = true;

    private TaskCompletionSource<Void> mDisplayTCS;

    private Host mHost;

    public TaskWorksController() {
        mDisplayTCS = new TaskCompletionSource<>();
    }

    /**
     * Create a Bolts task, where the next continuation will be executed when the UI is ready.
     *
     * @param task Bolts task.
     * @return A task that will returned the provided task termination when the UI is ready.
     */
    public <T> Task<T> createTask(Task<T> task) {
        return task.continueWithTask(new Continuation<T, Task<T>>() {
            @Override
            public Task<T> then(final Task<T> finished) throws Exception {
                if (mIsPaused) {
                    return getDisplayTCS().continueWithTask(new Continuation<Void, Task<T>>() {
                        @Override
                        public Task<T> then(Task<Void> task) throws Exception {
                            return finished;
                        }
                    },Task.UI_THREAD_EXECUTOR);
                } else {
                    return finished;
                }
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    /**
     * Create a Bolts task, where the next continuation will be executed when the UI is ready.
     * And attach the continutation directly as paramater.
     * <p>
     * This is a short hand of using createTask and add a continuation.
     *
     * @param task         Bolts task.
     * @param continuation Continuation to be executed when the task is finished.
     * @return A task that will returned the provided Task wrapping the continuation.
     */

    public <T, R> Task<R> addTask(final Task<T> task, Continuation<T, R> continuation) {
        return createTask(task).continueWith(continuation, Task.UI_THREAD_EXECUTOR);
    }

    private Task<Void> getDisplayTCS() {
        return mDisplayTCS.getTask();
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsPaused = false;

        mDisplayTCS.trySetResult(null);
    }

    @Override
    public void onPaused() {
        super.onPaused();

        mIsPaused = true;
        mDisplayTCS = new TaskCompletionSource<>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mDisplayTCS != null) {
            mDisplayTCS.trySetCancelled();
        }
    }

    public Host getHost() {
        return mHost;
    }

    protected void setHost(Host host) {
        boolean sendUpdate = mHost != host;

        this.mHost = host;
        if (sendUpdate) {
            onHostUpdated();
        }
    }

    protected void onHostUpdated() {

    }

    public static abstract class ControllerCallbacks<Controller extends TaskWorksController<Host>, Host> implements WorksControllerManager.ControllerCallbacks<Controller> {

        private Host mHost;

        public ControllerCallbacks(Host host) {
            mHost = host;
        }

        @Override
        public void onLoadFinished(int id, Bundle bundle, Controller controller) {
            controller.setHost(mHost);
        }
    }
}
