package com.example.bmorris.runtracker;

import android.support.v4.app.Fragment;

/**
 * Created by bmorris on 1/8/15.
 */
public class RunListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new RunListFragment();
    }
}
