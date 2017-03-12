package com.mobilesolutionworks.android.app.test.works;

import android.os.Bundle;

import com.mobilesolutionworks.android.app.WorksFragment;

/**
 * Created by yunarta on 12/3/17.
 */

public class StateWorksFragment extends WorksFragment {

    public enum State {
        BEGIN,
        CREATE,
        RESUME,
        PAUSE,
        DESTROY
    }

    protected State mState = State.BEGIN;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mState = State.CREATE;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        mState = State.PAUSE;
        super.onPause();
    }

    @Override
    public void onResume() {
        mState = State.RESUME;
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mState = State.DESTROY;
        super.onDestroy();
    }

    public State getState() {
        return mState;
    }
}
