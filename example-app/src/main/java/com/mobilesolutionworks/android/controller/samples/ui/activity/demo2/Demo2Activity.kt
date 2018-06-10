package com.mobilesolutionworks.android.controller.samples.ui.activity.demo2

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.mobilesolutionworks.android.app.WorksActivity

/**
 * Created by yunarta on 19/3/17.
 */

class Demo2Activity : WorksActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_demo2)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Demo2Fragment1())
                    .commit()
        }
    }
}