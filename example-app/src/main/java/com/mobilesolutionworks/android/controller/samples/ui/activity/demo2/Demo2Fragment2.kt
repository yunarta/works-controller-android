package com.mobilesolutionworks.android.controller.samples.ui.activity.demo2

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobilesolutionworks.android.app.WorksFragment

/**
 * Created by yunarta on 22/3/17.
 */

class Demo2Fragment2 : WorksFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.fragment_demo2_fragment2, null, false)
        return binding.root
    }
}
