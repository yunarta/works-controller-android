package com.mobilesolutionworks.android.controller.samples.ui.activity.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.mobilesolutionworks.android.app.WorksActivity
import com.mobilesolutionworks.android.app.controller.HostWorksController
import com.mobilesolutionworks.android.controller.samples.databinding.ActivityMainBinding

/**
 * Created by yunarta on 17/3/17.
 */

class MainActivity : WorksActivity() {

    private var mController: MainActivityController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mController = HostWorksController.create(this, 0, null, ::MainActivityController)

        val binding: ActivityMainBinding

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recycler.adapter = mController!!.adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        mController = null
    }
}
