package com.mobilesolutionworks.android.controller.test.sdk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.mobilesolutionworks.android.controller.test.R

/**
 * Created by yunarta on 9/3/17.
 */

public class RetainChildFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_host_activity)
        findViewById<Button>(R.id.button).setOnClickListener {
            supportFragmentManager.beginTransaction()
                  .addToBackStack("back-stack")
                  .replace(R.id.fragment_container, EmptyFragment(), "stack")
                  .commit()
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                  .replace(R.id.fragment_container, RootFragment(), "root")
                  .commitNow()
        }
    }
}
