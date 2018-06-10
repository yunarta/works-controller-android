package com.mobilesolutionworks.android.controller.samples.ui.activity.demo1

import android.os.Bundle
import android.util.Log
import com.mobilesolutionworks.android.app.controller.HostWorksController
import com.mobilesolutionworks.android.app.controller.WorksControllerManager

/**
 * Created by yunarta on 19/3/17.
 */

class Demo1ActivityController(manager: WorksControllerManager) : HostWorksController<Demo1Activity>(manager) {

    private var mThread: Thread? = null

    private var mRunning: Boolean = false

    private var mCounter: Int = 0

    override fun onCreate(arguments: Bundle?) {
        super.onCreate(arguments)

        mRunning = true
        mThread = Thread {
            while (mRunning && mCounter <= 9999) {
                mCounter++
                runWhenUiIsReady(Runnable { host?.postNumber(mCounter) })

                try {
                    Thread.sleep(66)
                } catch (e: InterruptedException) {
                    Log.d("[demo][#1]", "interrupted", e)
                }

            }
        }
        mThread!!.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mRunning = false
        mThread = null
    }
}
