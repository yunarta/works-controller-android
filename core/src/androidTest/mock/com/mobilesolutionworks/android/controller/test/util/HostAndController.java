//package com.mobilesolutionworks.android.controller.test.util;
//
//import com.mobilesolutionworks.android.app.controller.WorksController;
//import com.mobilesolutionworks.android.controller.test.GetController;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertNotNull;
//
///**
// * Created by yunarta on 12/3/17.
// */
//
//public class HostAndController<C extends WorksController> {
//
//    private boolean mChangeHost;
//
//    private C mController;
//
//    private int mHostHash;
//
//    private int mControllerHash;
//
//    public HostAndController() {
//        this(true);
//    }
//
//    public HostAndController(boolean changeHost) {
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
//    }
//
//    public C getController() {
//        return mController;
//    }
//}
