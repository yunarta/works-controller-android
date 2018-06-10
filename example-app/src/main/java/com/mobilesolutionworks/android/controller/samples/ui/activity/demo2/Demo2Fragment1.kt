package com.mobilesolutionworks.android.controller.samples.ui.activity.demo2

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobilesolutionworks.android.app.WorksFragment
import com.mobilesolutionworks.android.app.controller.HostWorksController
import com.mobilesolutionworks.android.controller.samples.databinding.FragmentDemo2Fragment1Binding

/**
 * Created by yunarta on 22/3/17.
 */

class Demo2Fragment1 : WorksFragment() {

    private var mController: Demo2Fragment1Controller? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("[demo][demo2]", this.toString() + " onCreate()")

        mController = HostWorksController.create(this, 0, null, ::Demo2Fragment1Controller)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("[demo][demo2]", this.toString() + " onCreateView() is called")

        val binding = DataBindingUtil.inflate<FragmentDemo2Fragment1Binding>(inflater, R.layout.fragment_demo2_fragment1, null, false)
        binding.fragment = this
        binding.controller = mController
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        Log.d("[demo][demo2]", this.toString() + " onPause()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("[demo][demo2]", this.toString() + " onResume()")
    }

    override fun onDestroy() {
        super.onDestroy()
        mController = null
    }

    fun dispatchStartActivityForResult() {
        startActivityForResult(Intent(context, GivingResultActivity::class.java), 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            Log.d("[demo][demo2]", this.toString() + " onActivityResult() is called")
            Log.d("[demo][demo2]", this.toString() + " fragment is in resumed state " + isResumed)
            val runnable = {
                view?.let {
                    DataBindingUtil.findBinding<FragmentDemo2Fragment1Binding>(it)?.apply {
                        ctrl2.setText(R.string.demo2_fragment1_withResult)
                    }
                }
            }

            if ("TO_CONTROLLER" == data?.action) {
                mController!!.runWhenUiIsReady(Runnable { runnable() })
            } else {
                runnable()
            }

            val pushFragment = data?.getBooleanExtra("push_fragment", true)
            if (pushFragment == true) {
                fragmentManager?.apply {
                    beginTransaction().addToBackStack(null)
                            .replace(R.id.fragment_container, Demo2Fragment2())
                            .commit()
                }
            }
        }
    }
}
