package com.mobilesolutionworks.android.controller.test.basic

import android.os.Bundle

import com.mobilesolutionworks.android.app.WorksFragment

/**
 * Created by yunarta on 12/3/17.
 */

open class SchedulerWorksFragment : WorksFragment() {

    enum class State {
        BEGIN,
        CREATE,
        RESUME,
        PAUSE,
        DESTROY
    }

    var state = State.BEGIN
        protected set

    override fun onCreate(savedInstanceState: Bundle?) {
        state = State.CREATE
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        state = State.PAUSE
        super.onPause()
    }

    override fun onResume() {
        state = State.RESUME
        super.onResume()
    }

    override fun onDestroy() {
        state = State.DESTROY
        super.onDestroy()
    }
}
