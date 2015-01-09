package com.example.bmorris.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

/**
 * Activity that holds the camera picture-taking page
 * Standard pattern: holds a single fragment where all the real work is done
 * Created by bmorris on 1/6/15.
 */
public class CrimeCameraActivity extends SingleFragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide the status bar and other OS-level chrome
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return new CrimeCameraFragment();
    }
}
