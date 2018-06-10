package com.mobilesolutionworks.android.controller.samples.ui.activity.demo2

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mobilesolutionworks.android.controller.samples.databinding.ActivityGivingResultBinding

/**
 * Created by yunarta on 22/3/17.
 */

class GivingResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binder: ActivityGivingResultBinding
        binder = DataBindingUtil.setContentView<ActivityGivingResultBinding>(this, R.layout.activity_giving_result)
        binder.activity = this
    }

    fun dispatchSetResult() {
        setResult(Activity.RESULT_OK, Intent("TO_FRAGMENT"))
        finish()
    }

    fun dispatchSetResult2() {
        setResult(Activity.RESULT_OK, Intent("TO_CONTROLLER"))
        finish()
    }

    fun dispatchSetResult3() {
        val intent = Intent("TO_FRAGMENT")
        intent.putExtra("push_fragment", false)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun dispatchSetResult4() {
        val intent = Intent("TO_CONTROLLER")
        intent.putExtra("push_fragment", false)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
