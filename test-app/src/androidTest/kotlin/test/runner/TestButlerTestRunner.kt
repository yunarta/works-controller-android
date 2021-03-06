package test.runner

import android.os.Bundle
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnitRunner
import com.linkedin.android.testbutler.TestButler

/**
 * Created by yunarta on 8/3/17.
 */

class TestButlerTestRunner : AndroidJUnitRunner() {

    override fun onStart() {
        TestButler.setup(InstrumentationRegistry.getTargetContext())
        super.onStart()
    }

    override fun finish(resultCode: Int, results: Bundle) {
        TestButler.teardown(InstrumentationRegistry.getTargetContext())
        super.finish(resultCode, results)
    }
}
