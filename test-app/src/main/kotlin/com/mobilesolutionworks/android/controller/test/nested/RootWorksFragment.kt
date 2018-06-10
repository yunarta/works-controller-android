package com.mobilesolutionworks.android.controller.test.nested

import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.mobilesolutionworks.android.controller.test.R

/**
 * Created by yunarta on 9/3/17.
 */

class RootWorksFragment : EmptyWorksFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                  .add(ChildWorksFragment(), "child")
                  .commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = view.findViewById(R.id.textView) as TextView
        id.text = "child"
    }

}
