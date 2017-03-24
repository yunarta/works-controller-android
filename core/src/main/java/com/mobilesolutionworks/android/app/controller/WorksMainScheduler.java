package com.mobilesolutionworks.android.app.controller;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by yunarta on 8/3/17.
 */

class WorksMainScheduler {

    private final Handler mHandler;

    private final PublicObservable mObservable;

    private boolean mIsPaused;

    WorksMainScheduler() {
        mHandler = new Handler(Looper.getMainLooper());
        mObservable = new PublicObservable();

        mIsPaused = true;
    }

    void resume() {
        mIsPaused = false;
        mObservable.setChanged();
        mObservable.notifyObservers();
    }

    void pause() {
        mIsPaused = true;
    }

    void release() {
        mObservable.deleteObservers();
    }

    /**
     * Run the specified runnable after the host enter resumed state.
     * <p>
     * This method will solve many problem like Fragment transaction problems,
     * or background updates that need to be notified to host.
     */
    void runOnUIWhenIsReady(@NonNull final Runnable runnable) {
        if (mIsPaused) {
            mObservable.addObserver(new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    mObservable.deleteObserver(this);
                    runOnMainThread(runnable);
                }
            });
        } else {
            runOnMainThread(runnable);
        }
    }

    /**
     * Run the specified runnable immediately if the caller is currently on main thread,
     * or it will be executed when the queue on main thread is available.
     * <p>
     * This execution does not guarantee the UI is ready, if you want to run something only when
     * the UI is ready use {@link #runOnUIWhenIsReady(Runnable)} instead.
     *
     * @param runnable runnable to run.
     */
    void runOnMainThread(@NonNull Runnable runnable) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }

    /**
     * Run the specified runnable delayed on main thread.
     * <p>
     * This execution does not guarantee the UI is ready, if you want to run something only when
     * the UI is ready use {@link #runOnUIWhenIsReady(Runnable)} instead.
     *
     * @param runnable runnable to run.
     */
    void runOnMainThreadDelayed(@NonNull Runnable runnable, long delay) {
        mHandler.postDelayed(runnable, delay);
    }

    private static final class PublicObservable extends Observable {

        @Override
        public void setChanged() {
            super.setChanged();
        }

    }

}
