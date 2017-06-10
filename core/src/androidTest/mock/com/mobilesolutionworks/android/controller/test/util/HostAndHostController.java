//package com.mobilesolutionworks.android.controller.test.util;
//
//
//import com.mobilesolutionworks.android.app.controller.HostWorksController;
//import com.mobilesolutionworks.android.controller.test.GetController;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertNotNull;
//
///**
// * Created by yunarta on 15/3/17.
// */
//
//public class HostAndHostController<C extends HostWorksController> {
//
//    private boolean mChangeHost;
//
//    private C mController;
//
//    private int mHostHash;
//
//    private int mControllerHash;
//
//    public HostAndHostController() {
//        this(true);
//    }
//
//    public HostAndHostController(boolean changeHost) {
//        mChangeHost = changeHost;
//    }
//
//    public void set(GetController<C> host) {
//        assertNotNull(host);
//
//        mController = host.getController();
//        assertNotNull(mController);
//
//        mHostHash = System.identityHashCode(host);
//        mControllerHash = System.identityHashCode(mController);
//    }
//
//    public void validate(GetController<C> host) {
//        assertNotNull(host);
//        assertNotNull(host.getController());
//
//        if (mChangeHost) {
//            assertNotEquals("Host changed after rotation", mHostHash, System.identityHashCode(host));
//        } else {
//            assertEquals("Host didn't changed after rotation", mHostHash, System.identityHashCode(host));
//        }
//
//        assertEquals("Controller is not retain during rotation", mControllerHash, System.identityHashCode(host.getController()));
//
//        assertEquals("Host in controller is not the same with host that creates", host, mController.getHost());
//    }
//
//    public C getController() {
//        return mController;
//    }
//}
