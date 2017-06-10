package com.mobilesolutionworks.android.app.controller

import android.os.Handler
import android.os.Looper
import android.util.Log

import java.util.Observable
import java.util.Observer

/**
 * Created by yunarta on 8/3/17.
 */

internal class WorksMainScheduler {

    private val mHandler: Handler

    private val mObservable: PublicObservable

    private var mIsPaused: Boolean = false

    init {
        mHandler = Handler(Looper.getMainLooper())
        mObservable = PublicObservable()

        mIsPaused = true
    }

    fun resume() {
        mIsPaused = false
        mObservable.setChanged()
        mObservable.notifyObservers()
    }

    fun pause() {
        mIsPaused = true
    }

    fun release() {
        mObservable.deleteObservers()
    }

    /**
     * Run the specified runnable after the host enter resumed state.
     *
     *
     * This method will solve many problem like Fragment transaction problems,
     * or background updates that need to be notified to host.
     */
    fun runWhenUiIsReady(runnable: Runnable) {
        if (mIsPaused) {
            mObservable.addObserver(object : Observer {
                override fun update(o: Observable, arg: Any?) {
                    mObservable.deleteObserver(this)
                    runOnMainThread(runnable)
                }
            })
        } else {
            runOnMainThread(runnable)
        }
    }

    /**
     * Run the specified runnable immediately if the caller is currently on main thread,
     * or it will be executed when the queue on main thread is available.
     *
     *
     * This execution does not guarantee the UI is ready, if you want to run something only when
     * the UI is ready use [.runWhenUiIsReady] instead.

     * @param runnable runnable to run.
     */
    fun runOnMainThread(runnable: Runnable) {
        if (Looper.getMainLooper().thread === Thread.currentThread()) {
            runnable.run()
        } else {
            mHandler.post(runnable)
        }
    }

    /**
     * Run the specified runnable delayed on main thread.
     *
     *
     * This execution does not guarantee the UI is ready, if you want to run something only when
     * the UI is ready use [.runWhenUiIsReady] instead.

     * @param runnable runnable to run.
     */
    fun runOnMainThreadDelayed(runnable: Runnable, delay: Long) {
        mHandler.postDelayed(runnable, delay)
    }

    private class PublicObservable : Observable() {

        public override fun setChanged() {
            super.setChanged()
        }

    }

}
