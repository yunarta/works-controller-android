package com.mobilesolutionworks.android.controller.test.sdk

import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.mobilesolutionworks.android.controller.test.R

/**
 * Created by yunarta on 9/3/17.
 */

class ChildFragment : EmptyFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = view.findViewById(R.id.textView) as TextView
        id.text = "child"
    }
}
