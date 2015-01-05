package com.example.bmorris.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by bmorris on 1/5/15.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
