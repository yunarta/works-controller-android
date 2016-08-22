package com.mobilesolutionworks.android.controller.samples.sample1;

import com.mobilesolutionworks.android.app.controller.TaskWorksController;

import java.util.concurrent.atomic.AtomicReference;

import bolts.Continuation;
import bolts.Task;

/**
 * A very simple controller that counts and post the result on the screen.
 * <p>
 * Created by yunarta on 23/8/16.
 */
public class CountingController extends TaskWorksController<CountingActivity> {

    private AtomicReference<Thread> mThread;

    public CountingController() {
        mThread = new AtomicReference<>();
    }

    public void startCounting() {
        if (mThread.get() == null) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 1; i <= 100; i++) {
                        // post the result into task controller
                        addTask(Task.forResult(i), new Continuation<Integer, Object>() {
                            @Override
                            public Object then(Task<Integer> task) throws Exception {
                                if (getHost() != null) {
                                    getHost().postNumber(task.getResult());
                                }
                                return null;
                            }
                        });

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            // e.printStackTrace();
                        }
                    }

                    mThread.set(null);
                }
            });

            mThread.set(thread);
            thread.start();
        }
    }
}
